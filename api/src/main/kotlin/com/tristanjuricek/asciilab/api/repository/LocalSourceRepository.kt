package com.tristanjuricek.asciilab.api.repository

import com.tristanjuricek.asciilab.api.model.LocalSource
import com.tristanjuricek.asciilab.api.repository.schema.LocalSourceEntity
import com.tristanjuricek.asciilab.api.repository.schema.LocalSourceTable
import com.tristanjuricek.asciilab.api.repository.schema.SourceEntity
import org.jetbrains.exposed.sql.transactions.transaction

interface LocalSourceRepository {

    fun findAll(): List<LocalSource>

    fun findByID(id: Int): LocalSource?

    fun save(localSource: LocalSource): LocalSource

    fun deleteByID(id: Int)

    fun deleteBySourceID(id: Int)

}

class JDBCLocalSourceRepository() : LocalSourceRepository {

    override fun deleteByID(id: Int) {
        transaction {
            LocalSourceEntity[id].delete()
        }
    }

    override fun deleteBySourceID(id: Int) {
        transaction {
            val sources = LocalSourceEntity.find { LocalSourceTable.sourceRef.eq(id) }.toList()
            sources.forEach { it.delete() }
        }
    }

    override fun findAll(): List<LocalSource> {
        return transaction {
            LocalSourceEntity.all()
                    .map { it.toModel() }
                    .toList()
        }
    }

    override fun findByID(id: Int): LocalSource? {
        return transaction {
            LocalSourceEntity.findById(id)?.toModel()
        }
    }

    override fun save(localSource: LocalSource): LocalSource {
        return transaction {
            when (localSource.id) {
                -1 -> LocalSourceEntity.new {
                    source = SourceEntity.findById(localSource.source.id)!!
                    localURL = localSource.localURL
                }.toModel()

                else -> {
                    val localEntity = LocalSourceEntity.get(localSource.id)

                    if (localSource.source != null) {
                        localEntity.source = SourceEntity.findById(localSource.source.id)!!
                    }

                    if (localSource.localURL != null) {
                        localEntity.localURL = localSource.localURL
                    }

                    localEntity.toModel()
                }
            }
        }
    }
}