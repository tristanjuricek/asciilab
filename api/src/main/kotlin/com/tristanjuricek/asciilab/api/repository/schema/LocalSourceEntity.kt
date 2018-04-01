package com.tristanjuricek.asciilab.api.repository.schema

import com.tristanjuricek.asciilab.api.model.LocalSource
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

class LocalSourceEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<LocalSourceEntity>(LocalSourceTable)

    var localURL by LocalSourceTable.localURL
    var source by SourceEntity referencedOn LocalSourceTable.sourceRef

    fun toModel(): LocalSource = LocalSource(id.value, source.toModel(), localURL)
}

object LocalSourceTable : IntIdTable() {
    val localURL = text("local_url")
    val sourceRef = reference("source", SourceTable)
}