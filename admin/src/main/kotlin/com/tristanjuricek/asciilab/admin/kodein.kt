package com.tristanjuricek.asciilab.admin

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.tristanjuricek.asciilab.admin.handlers.HandleListSources
import com.tristanjuricek.asciilab.admin.views.SourcesView
import com.tristanjuricek.asciilab.api.client.APIClient
import com.tristanjuricek.asciilab.api.client.APIWebClientImpl
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature


fun kodein(application: Application) = Kodein {
    bind<APIClient>() with singleton {
        val httpClient = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
        APIWebClientImpl(application.environment.config.property("api.url").getString(), httpClient)
    }
    bind<HandleListSources>() with singleton { HandleListSources(this) }
    bind<SourcesView>() with singleton { SourcesView() }
}

lateinit var theKodein: Kodein
