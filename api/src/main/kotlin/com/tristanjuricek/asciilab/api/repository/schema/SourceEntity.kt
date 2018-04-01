package com.tristanjuricek.asciilab.api.repository.schema

import com.tristanjuricek.asciilab.api.model.Source
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

class SourceEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SourceEntity>(SourceTable)

    var name by SourceTable.name
    var url by SourceTable.url

    fun toModel() : Source = Source(
            id.value,
            name,
            url
    )
}

object SourceTable : IntIdTable() {
    val name = text("name")
    val url = text("url")
}