package com.tristanjuricek.asciilab.api

import com.tristanjuricek.asciilab.api.Config.sourceRepository
import com.tristanjuricek.asciilab.api.Config.sourceRepositoryService
import com.tristanjuricek.asciilab.api.model.Sources
import com.tristanjuricek.asciilab.api.repository.schema.initializeSchema
import io.ktor.application.Application
import io.ktor.application.ApplicationStopping
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
    Config.application = this

    initDatabaseDriver()
    val dataSource = createDataSource(environment.config.property("db.uri").getString())
    Database.connect(dataSource)
    initializeSchema()

    environment.monitor.subscribe(ApplicationStopping, { _ -> dataSource.close() })

    install(ContentNegotiation) {
        gson {
        }
    }
    routing {
        get("/sources") {
            call.respond(Sources(sourceRepositoryService.findAll()))
        }
        post("/sources") {
            sourceRepositoryService.save(call.receive())
            call.respond(emptyMap<String, Any>())
        }
        get("/sources/{id}") {
            call.respond(sourceRepositoryService.findByID(call.parameters["id"]!!.toInt()) ?: emptyMap<String, Any>())
        }
        delete("/sources/{id}") {
            sourceRepositoryService.deleteByID(call.parameters["id"]!!.toInt())
            call.respond(emptyMap<String, Any>())
        }
    }
}

