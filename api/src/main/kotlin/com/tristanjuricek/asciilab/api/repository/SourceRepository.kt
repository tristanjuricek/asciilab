package com.tristanjuricek.asciilab.api.repository

import com.tristanjuricek.asciilab.api.model.Source
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SourceRepository {

    fun deleteByID(id: Int): Mono<Unit>

    fun findAll(): Flux<Source>

    fun findByID(id: Int): Mono<Source?>

    fun save(source: Source): Mono<Source>
}