package com.tristanjuricek.asciilab.api.repository

import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.repository.schema.SourceEntity
import kotlinx.coroutines.experimental.reactor.flux
import kotlinx.coroutines.experimental.reactor.mono
import org.jetbrains.exposed.sql.transactions.transaction
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


class JDBCSourceRepository : SourceRepository {

    override fun deleteByID(id: Int): Mono<Unit> = mono {
        transaction {
            SourceEntity[id].delete()
        }
        null
    }

    override fun findAll(): Flux<Source> = flux {
        val sources = transaction {
            SourceEntity.all()
                    .map { it.toModel() }
                    .toList() // I'm pretty sure we need to allocate these things
        }
        for (source in sources) {
            send(source)
        }
    }

    override fun findByID(id: Int): Mono<Source?> = mono {
        transaction {
            SourceEntity.findById(id)?.toModel()
        }
    }

    override fun save(source: Source): Mono<Source> = mono {
        transaction {
            when (source.id) {
                -1 -> SourceEntity.new {
                    name = source.name
                    url = source.url
                }.toModel()

                else -> {
                    val entity = SourceEntity.get(source.id)
                    if (entity.name != source.name) {
                        entity.name = source.name
                    }
                    if (entity.url != source.url) {
                        entity.url = source.url
                    }
                    entity.toModel()
                }
            }
        }
    }
}