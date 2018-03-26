package com.tristanjuricek.asciilab.api.repository

import com.tristanjuricek.asciilab.api.model.Source

interface SourceRepository {

    fun deleteByID(id: Int)

    fun findAll(): List<Source>

    fun findByID(id: Int): Source?

    fun save(source: Source): Source
}