package com.tristanjuricek.asciilab.api.services

import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.repository.LocalSourceRepository
import com.tristanjuricek.asciilab.api.repository.SourceRepository

class SourceRepositoryService(private val sourceRepository: SourceRepository,
                              private val localSourceService: LocalSourceService,
                              private val localSourceRepository: LocalSourceRepository): SourceRepository by sourceRepository {


    override fun deleteByID(id: Int) {
        localSourceRepository.deleteBySourceID(id)
        sourceRepository.deleteByID(id)
    }

    override fun save(source: Source): Source {
        val src = sourceRepository.save(source)
        localSourceService.createLocalSource(src)
        return src
    }
}