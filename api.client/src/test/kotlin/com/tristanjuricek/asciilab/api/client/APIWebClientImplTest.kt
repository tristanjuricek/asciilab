package com.tristanjuricek.asciilab.api.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.model.Sources
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.content.IncomingContent
import io.ktor.content.OutgoingContent
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import kotlin.reflect.KClass

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class APIWebClientImplTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var httpClient: HttpClient

    private lateinit var apiClient: APIClient

    private val objectMapper: ObjectMapper by lazy { jacksonObjectMapper() }

    @BeforeAll
    fun setup() {
        mockWebServer = MockWebServer()

        httpClient = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        apiClient = APIWebClientImpl(mockWebServer.url("/").toString(), httpClient)
    }

    @AfterAll
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GET sources can return a list of Source`() {
        val sources = listOf(
                Source(1, "first", "http://example.com/first"),
                Source(2, "second", "http://example.com/second")
        )

        val json = objectMapper.writeValueAsString(Sources(sources))

        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(json))

        val actual = runBlocking {
            apiClient.listSources()
        }

        Assertions.assertEquals(sources, actual)
    }

    @Test
    fun `POST sources will accept a new Source`() {
        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody("{}"))

        val source = Source(name = "test", url = "http://example.com/test")

        runBlocking { apiClient.createSource(source) }
    }

    @Test
    fun `GET sources {id} returns a Source`() {
        val source = Source(1, "first", "http://example.com/first")

        val json = objectMapper.writeValueAsString(source)

        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(json))

        val actual = runBlocking {
            apiClient.findSource(1)
        }

        Assertions.assertEquals(source, actual)
    }

    @Test
    fun `DELETE sources {id} can delete a Source by ID`() {
        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json"))

        runBlocking {
            apiClient.deleteSource(1)
        }
    }
}