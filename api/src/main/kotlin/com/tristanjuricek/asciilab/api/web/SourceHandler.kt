package com.tristanjuricek.asciilab.api.web

import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.repository.SourceRepository
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

class SourceHandler(private val sourceRepository: SourceRepository) {

    fun listSources(serverRequest: ServerRequest): Mono<ServerResponse> =
        ok().body(BodyInserters.fromPublisher(sourceRepository.findAll(), Source::class.java))


    fun findSource(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id").toInt()
        return sourceRepository.findByID(id).flatMap {
            it?.let { ok().body(BodyInserters.fromObject(it)) } ?: notFound().build()
        }
    }

    fun createSource(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(Source::class.java).flatMap { source ->
            sourceRepository.save(source).flatMap { saved -> ok().body(BodyInserters.fromObject(saved)) }
        }
    }

    fun deleteSource(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id").toInt()
        return sourceRepository.deleteByID(id).flatMap { _ -> ok().build() }
    }
}