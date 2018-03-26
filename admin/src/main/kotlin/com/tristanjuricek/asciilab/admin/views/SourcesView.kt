package com.tristanjuricek.asciilab.admin.views

import com.tristanjuricek.asciilab.api.model.Source
import kotlinx.html.*

class SourcesView : AppView {

    fun render(html: HTML, sources: List<Source>) {
        html.apply {
            header(this, "Sources")
            body {
                h1 { text("Sources") }
                div {
                    a(href = "/asciilab/admin/sources/new") { text("Create New") }
                }
                sources(sources)
            }
        }
    }

    fun BODY.sources(models: List<Source>) {
        models.forEach { model ->
            div("source") {
                text(model.name)
                text(" - ")
                text(model.url)
                br
                form(method = FormMethod.post, action = "/asciilab/admin/sources/delete") {
                    input(type = InputType.hidden, name = "id") {
                        value = model.id.toString()
                    }
                    submitInput { value = "Delete" }
                }
            }
        }
    }
}