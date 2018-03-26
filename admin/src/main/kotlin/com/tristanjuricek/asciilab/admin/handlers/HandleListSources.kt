package com.tristanjuricek.asciilab.admin.handlers

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.tristanjuricek.asciilab.admin.views.SourcesView
import com.tristanjuricek.asciilab.api.client.APIClient
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import kotlinx.coroutines.experimental.runBlocking

class HandleListSources(kodein: Kodein) {

    private val apiClient: APIClient = kodein.instance()

    private val sourcesView: SourcesView = kodein.instance()

    fun handle(call: ApplicationCall) = runBlocking {
        val sources = apiClient.listSources()
        call.respondHtml { sourcesView.render(this, sources) }
    }
}