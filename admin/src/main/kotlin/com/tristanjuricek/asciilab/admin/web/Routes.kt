package com.tristanjuricek.asciilab.admin.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse

class Routes {

//    constructor() {
//
//    }

    fun router() = org.springframework.web.reactive.function.server.router {
        accept(MediaType.TEXT_HTML).nest {
            GET("/hello", { _ -> ServerResponse.ok().syncBody("<html><body><h1>HI</h1></body></html>") })
        }
    }
}