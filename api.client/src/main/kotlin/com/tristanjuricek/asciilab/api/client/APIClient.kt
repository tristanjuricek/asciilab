package com.tristanjuricek.asciilab.api.client

import com.tristanjuricek.asciilab.api.model.Source
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface APIClient {

    fun listSources(): Flux<Source>

    fun createSource(source: Mono<Source>): Mono<Void>

    fun findSource(id: Int): Mono<Source>

    fun deleteSource(id: Int): Mono<Void>
}