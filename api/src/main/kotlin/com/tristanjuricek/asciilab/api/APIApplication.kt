package com.tristanjuricek.asciilab.api

import com.tristanjuricek.asciilab.api.web.Routes
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class APIApplication {

    private val httpHandler: HttpHandler

    private val server: HttpServer

    private var nettyContext: BlockingNettyContext? = null

    constructor(port: Int = 8080) {
        val context = GenericApplicationContext()
        beans {
            bean<Routes>()
            bean("webHandler") {
                RouterFunctions.toWebHandler(ref<Routes>().router(), HandlerStrategies.withDefaults())
            }
            initialize(context)
        }
        context.refresh()


        server = HttpServer.create(port)

        httpHandler = WebHttpHandlerBuilder
                .applicationContext(context)
                .build()
    }

    fun start() {
        nettyContext = server.start(ReactorHttpHandlerAdapter(httpHandler))
    }

    fun startAndAwait() {
        server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler), { nettyContext = it })
    }

    fun stop() {
        nettyContext?.shutdown()
    }
}

fun main(args: Array<String>) {
    APIApplication().startAndAwait()
}

