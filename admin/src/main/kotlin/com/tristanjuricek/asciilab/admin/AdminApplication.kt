package com.tristanjuricek.asciilab.admin

import com.tristanjuricek.asciilab.admin.Config.sourceHandlers
import com.tristanjuricek.asciilab.admin.handlers.SourceHandlers
import com.tristanjuricek.asciilab.admin.views.CreateSourceView
import com.tristanjuricek.asciilab.admin.views.SourcesView
import com.tristanjuricek.asciilab.api.client.APIClient
import com.tristanjuricek.asciilab.api.client.APIWebClientImpl
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

object Config {
    lateinit var application: Application

    val httpClient:HttpClient by lazy {
        HttpClient(Apache) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }

    val apiUrl : String by lazy {
        application.environment.config.property("api.url").getString()
    }

    val apiClient: APIClient by lazy {
        APIWebClientImpl(apiUrl, httpClient)
    }

    val sourcesView: SourcesView by lazy {
        SourcesView()
    }

    val createSourceView: CreateSourceView by lazy {
        CreateSourceView()
    }

    val sourceHandlers: SourceHandlers by lazy {
        SourceHandlers(apiClient, sourcesView, createSourceView)
    }

}



fun Application.admin() {
    Config.application = this

    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/sources") {
            sourceHandlers.listSources(call)
        }
        get("/sources/new") {
            sourceHandlers.createSourceForm(call)
        }
        post("/sources/new") {
            sourceHandlers.createSource(call)
        }
        post("/sources/delete") {
            sourceHandlers.deleteSource(call)
        }
    }
}

