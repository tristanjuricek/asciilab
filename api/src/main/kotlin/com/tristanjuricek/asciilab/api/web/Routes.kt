package com.tristanjuricek.asciilab.api.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class Routes(private val sourceHandler: SourceHandler) {

    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/sources", sourceHandler::listSources)
            POST("/sources", sourceHandler::createSource)
            GET("/sources/{id}", sourceHandler::findSource)
            DELETE("/sources/{id}", sourceHandler::deleteSource)
        }
    }
