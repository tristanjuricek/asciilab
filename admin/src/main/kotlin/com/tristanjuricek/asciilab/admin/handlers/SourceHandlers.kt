package com.tristanjuricek.asciilab.admin.handlers

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.tristanjuricek.asciilab.admin.views.CreateSourceView
import com.tristanjuricek.asciilab.admin.views.SourcesView
import com.tristanjuricek.asciilab.api.client.APIClient
import com.tristanjuricek.asciilab.api.model.Source
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import kotlinx.coroutines.experimental.runBlocking

class SourceHandlers(kodein: Kodein) {

    private val apiClient: APIClient = kodein.instance()

    private val sourcesView: SourcesView = kodein.instance()

    private val createSourceView: CreateSourceView = kodein.instance()

    fun listSources(call: ApplicationCall) = runBlocking {
        val sources = apiClient.listSources()
        call.respondHtml { sourcesView.render(this, sources) }
    }

    fun createSourceForm(call: ApplicationCall) = runBlocking {
        call.respondHtml { createSourceView.render(this) }
    }

    fun createSource(call: ApplicationCall) = runBlocking {
        val parameters = call.receive<Parameters>()
        val source = Source(name = parameters["name"]!!, url = parameters["url"]!!)
        apiClient.createSource(source)
        call.respondRedirect("/asciilab/admin/sources")
    }

    fun deleteSource(call: ApplicationCall) = runBlocking {
        val parameters = call.receiveParameters()
        apiClient.deleteSource(parameters["id"]?.toInt() ?: throw IllegalStateException("id must be specified"))
        call.respondRedirect("/asciilab/admin/sources")
    }
}