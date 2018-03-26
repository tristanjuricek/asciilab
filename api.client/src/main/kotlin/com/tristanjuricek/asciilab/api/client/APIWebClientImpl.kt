package com.tristanjuricek.asciilab.api.client

import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.model.Sources
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.*
import java.net.URL

class APIWebClientImpl(private val baseUrl: String, private val httpClient: HttpClient) : APIClient {

    override suspend fun listSources(): List<Source> {
        val sources = httpClient.get<Sources>(buildUrl("/sources"))
        return sources.sources
    }

    override suspend fun createSource(source: Source) {
        httpClient.post<Unit> {
            url(buildUrl("/sources"))
            contentType(ContentType.Application.Json)
            body = source
        }
    }

    override suspend fun findSource(id: Int): Source? {
        return httpClient.get(buildUrl("/sources/$id"))
    }

    override suspend fun deleteSource(id: Int) {
        httpClient.call(buildUrl("/sources/$id")) {
            method = HttpMethod.Delete
        }
    }

    private fun buildUrl(path: String): URL {
        return URL(baseUrl + path)
    }
}