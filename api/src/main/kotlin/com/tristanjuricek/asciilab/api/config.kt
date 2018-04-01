package com.tristanjuricek.asciilab.api

import com.tristanjuricek.asciilab.api.repository.JDBCLocalSourceRepository
import com.tristanjuricek.asciilab.api.repository.JDBCSourceRepository
import com.tristanjuricek.asciilab.api.repository.LocalSourceRepository
import com.tristanjuricek.asciilab.api.repository.SourceRepository
import com.tristanjuricek.asciilab.api.services.LocalSourceService
import com.tristanjuricek.asciilab.api.services.LocalSourceServiceImpl
import com.tristanjuricek.asciilab.api.services.SourceRepositoryService
import io.ktor.application.Application

object Config {
    lateinit var application: Application

    val workingDirectory: String by lazy {
        application.environment.config.property("working_dir").getString()
    }

    val sourceRepository: SourceRepository by lazy {
        JDBCSourceRepository()
    }

    val localSourceRepository: LocalSourceRepository by lazy {
        JDBCLocalSourceRepository()
    }

    val localSourceService: LocalSourceService by lazy {
        LocalSourceServiceImpl(localSourceRepository, workingDirectory)
    }

    val sourceRepositoryService: SourceRepositoryService by lazy {
        SourceRepositoryService(sourceRepository, localSourceService, localSourceRepository)
    }
}