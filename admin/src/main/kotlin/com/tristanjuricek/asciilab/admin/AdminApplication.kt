package com.tristanjuricek.asciilab.admin

import com.github.salomonbrys.kodein.instance
import com.tristanjuricek.asciilab.admin.handlers.HandleListSources
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.html.respondHtml
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title

fun Application.admin() {
    theKodein = kodein(this)

    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/hello") {
            call.respondHtml {
                head {
                    title("Hello")
                }
                body {
                    h1 { text("Hello, Kotlin") }
                }
            }
        }
        get("/sources") {
            theKodein.instance<HandleListSources>().handle(call)
        }
    }
}

