package com.tristanjuricek.asciilab.admin

import com.github.salomonbrys.kodein.instance
import com.tristanjuricek.asciilab.admin.handlers.SourceHandlers
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun Application.admin() {
    theKodein = kodein(this)

    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/sources") {
            theKodein.instance<SourceHandlers>().listSources(call)
        }
        get("/sources/new") {
            theKodein.instance<SourceHandlers>().createSourceForm(call)
        }
        post("/sources/new") {
            theKodein.instance<SourceHandlers>().createSource(call)
        }
        post("/sources/delete") {
            theKodein.instance<SourceHandlers>().deleteSource(call)
        }
    }
}

