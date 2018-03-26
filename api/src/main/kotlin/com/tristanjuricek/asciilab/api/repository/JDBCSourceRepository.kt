package com.tristanjuricek.asciilab.api.repository

import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.repository.schema.SourceEntity
import org.jetbrains.exposed.sql.transactions.transaction


class JDBCSourceRepository : SourceRepository {

    override fun deleteByID(id: Int) {
        transaction {
            SourceEntity[id].delete()
        }
    }

    override fun findAll(): List<Source> {
        return transaction {
            SourceEntity.all()
                    .map { it.toModel() }
                    .toList() // I'm pretty sure we need to allocate these things
        }
    }

    override fun findByID(id: Int): Source? {
        return transaction {
            SourceEntity.findById(id)?.toModel()
        }
    }

    override fun save(source: Source): Source {
        return transaction {
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