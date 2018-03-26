package com.tristanjuricek.asciilab.api.client

import com.tristanjuricek.asciilab.api.model.Source

interface APIClient {

    suspend fun listSources(): List<Source>

    suspend fun createSource(source: Source)

    suspend fun findSource(id: Int): Source?

    suspend fun deleteSource(id: Int)
}