package com.tristanjuricek.asciilab.admin.web.view

import org.springframework.context.ApplicationContext
import org.springframework.web.reactive.result.view.View
import org.springframework.web.reactive.result.view.ViewResolver
import reactor.core.publisher.Mono
import java.util.*

/**
 * This just uses the view name to resolve to a "view" bean.
 */
class KotlinViewResolver(private val context: ApplicationContext) : ViewResolver {

    override fun resolveViewName(viewName: String, locale: Locale): Mono<View> {
        // TODO: Maybe make the configuration a bit more obvious by using more of the MonoSink API
        return Mono.create { sink ->
            val view = context.getBean(viewName!!, View::class.java)
            sink.success(view)
        }
    }
}