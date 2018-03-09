package com.tristanjuricek.asciilab.web

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

class Routes {

//    constructor() {
//
//    }

    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/hello", { _ -> ok().syncBody("{ hi }") })
        }
    }
}