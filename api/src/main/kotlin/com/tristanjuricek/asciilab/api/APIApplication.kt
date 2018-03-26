package com.tristanjuricek.asciilab.api

import com.github.salomonbrys.kodein.instance
import com.tristanjuricek.asciilab.api.model.Sources
import com.tristanjuricek.asciilab.api.repository.SourceRepository
import com.tristanjuricek.asciilab.api.repository.schema.initializeSchema
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database


fun Application.api() {
    initDatabaseDriver()
    val dataSource = createDataSource(environment.config.property("db.uri").getString())
    Database.connect(dataSource)
    initializeSchema()

    install(ContentNegotiation) {
        gson {
        }
    }
    routing {
        get("/sources") {
            call.respond(Sources(kodein.instance<SourceRepository>().findAll()))
        }
        post("/sources") {
            kodein.instance<SourceRepository>().save(call.receive())
            call.respond(emptyMap<String, Any>())
        }
        get("/sources/{id}") {
            call.respond(kodein.instance<SourceRepository>().findByID(call.parameters["id"]!!.toInt()) ?: emptyMap<String, Any>())
        }
        delete("/sources/{id}") {
            kodein.instance<SourceRepository>().deleteByID(call.parameters["id"]!!.toInt())
            call.respond(emptyMap<String, Any>())
        }
    }
}

