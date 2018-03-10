package com.tristanjuricek.asciilab.admin

import com.tristanjuricek.asciilab.admin.web.Routes
import com.tristanjuricek.asciilab.admin.web.view.HelloView
import com.tristanjuricek.asciilab.admin.web.view.KotlinViewResolver
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions

fun beans(context: GenericApplicationContext) = beans {

    bean<Routes>()
    bean("webHandler") {
        val strategies = HandlerStrategies.builder()
                .viewResolver(KotlinViewResolver(context))
                .build()
        RouterFunctions.toWebHandler(ref<Routes>().router(), strategies)
    }

    bean<HelloView>("HelloView")
}