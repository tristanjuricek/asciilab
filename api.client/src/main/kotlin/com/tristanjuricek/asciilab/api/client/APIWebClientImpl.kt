package com.tristanjuricek.asciilab.api.client

import com.tristanjuricek.asciilab.api.model.Source
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class APIWebClientImpl(private val webClient: WebClient) : APIClient {

    override fun listSources(): Flux<Source> =
        webClient.get()
                .uri("/sources")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux()

    override fun createSource(source: Mono<Source>): Mono<Void> =
            webClient.post()
                    .uri("/sources")
                    .body(BodyInserters.fromObject(source))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono()

    override fun findSource(id: Int): Mono<Source> =
            webClient.get()
                    .uri("/sources/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono()

    override fun deleteSource(id: Int): Mono<Void> =
            webClient.delete()
                    .uri("/sources/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono()
}