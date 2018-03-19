package com.tristanjuricek.asciilab.api

import com.tristanjuricek.asciilab.api.repository.JDBCSourceRepository
import com.tristanjuricek.asciilab.api.repository.SourceRepository
import com.tristanjuricek.asciilab.api.web.Routes
import com.tristanjuricek.asciilab.api.web.SourceHandler
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.core.env.get
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions

fun beans(context: GenericApplicationContext): BeanDefinitionDsl = beans {
    val env = context.environment

    bean {
        val url = if (env.containsProperty("db.url")) env["db.url"] else "jdbc:postgresql://localhost:5432/asciiapi"
        DbConfig(url)
    }

    bean<Routes>()
    bean<SourceHandler>()
    bean<SourceRepository> {
        JDBCSourceRepository()
    }
    bean("webHandler") {
        RouterFunctions.toWebHandler(ref<Routes>().router(), HandlerStrategies.withDefaults())
    }
    initialize(context)
}