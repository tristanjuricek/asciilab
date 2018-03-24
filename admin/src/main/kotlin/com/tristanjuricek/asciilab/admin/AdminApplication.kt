package com.tristanjuricek.asciilab.admin

import com.tristanjuricek.asciilab.admin.web.view.KotlinViewResolver
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.config.ViewResolverRegistry
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class AdminApplication {

    private val httpHandler: HttpHandler

    private val server: HttpServer

    private var nettyContext: BlockingNettyContext? = null

    var context: GenericApplicationContext = GenericApplicationContext()

    constructor(port: Int = 8082) {
        context.apply {
            beans(this).initialize(this)
            refresh()
        }

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
    AdminApplication().startAndAwait()
}

