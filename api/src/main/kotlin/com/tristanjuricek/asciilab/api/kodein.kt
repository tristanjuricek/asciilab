package com.tristanjuricek.asciilab.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.tristanjuricek.asciilab.api.repository.JDBCSourceRepository
import com.tristanjuricek.asciilab.api.repository.SourceRepository

val kodein = Kodein {
    bind<SourceRepository>() with singleton { JDBCSourceRepository() }
}