package com.tristanjuricek.asciilab.api.repository.schema

import org.jetbrains.exposed.dao.IntIdTable

object SourceTable : IntIdTable() {
    val name = text("name")
    val url = text("url")
}