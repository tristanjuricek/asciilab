package com.tristanjuricek.asciilab.admin.web.view

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.http.MediaType
import org.springframework.web.reactive.result.view.View
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class HelloView : View {

    override fun render(model: MutableMap<String, *>?, contentType: MediaType?, exchange: ServerWebExchange): Mono<Void> {
        val dataBuffer = exchange.response.bufferFactory().allocateBuffer()

        dataBuffer.asOutputStream().bufferedWriter().use { writer ->
            writer.appendHTML().html {
                head {
                    title("Hello")
                }
                body {
                    h1 {
                        text("Hi, From Kotlin")
                    }
                }
            }
        }

        return exchange.response.writeWith(Mono.just(dataBuffer))
    }
}