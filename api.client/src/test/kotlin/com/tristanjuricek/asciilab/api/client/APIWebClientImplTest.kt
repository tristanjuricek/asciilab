package com.tristanjuricek.asciilab.api.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tristanjuricek.asciilab.api.model.Source
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class APIWebClientImplTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var webClient: WebClient

    private lateinit var apiClient: APIClient

    private val objectMapper: ObjectMapper by lazy { jacksonObjectMapper() }

    @BeforeAll
    fun setup() {
        mockWebServer = MockWebServer()
        webClient = WebClient.create(mockWebServer.url("/").toString())
        apiClient = APIWebClientImpl(webClient)
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

        val json = objectMapper.writeValueAsString(sources)

        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(json))

        val actual = apiClient.listSources()

        StepVerifier.create(actual)
                .expectNext(sources[0])
                .expectNext(sources[1])
                .verifyComplete()
    }

    @Test
    fun `POST sources will accept a new Source`() {
        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json"))

        val source = Source(name = "test", url = "http://example.com/test")

        val response = apiClient.createSource(Mono.just(source))

        StepVerifier.create(response)
                .verifyComplete()
    }

    @Test
    fun `GET sources {id} returns a Source`() {
        val source = Source(1, "first", "http://example.com/first")

        val json = objectMapper.writeValueAsString(source)

        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(json))

        val actual = apiClient.findSource(1)

        StepVerifier.create(actual)
                .expectNext(source)
                .verifyComplete()
    }

    @Test
    fun `DELETE sources {id} can delete a Source by ID`() {
        mockWebServer.enqueue(MockResponse()
                .addHeader("Content-Type", "application/json"))

        val response = apiClient.deleteSource(1)

        StepVerifier.create(response)
                .verifyComplete()
    }
}