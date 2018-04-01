package com.tristanjuricek.asciilab.api.repository.schema

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create

fun initializeSchema() {
    transaction {
        create(SourceTable)
        create(LocalSourceTable)
    }
}