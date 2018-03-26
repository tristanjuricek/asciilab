package com.tristanjuricek.asciilab.admin.views

import kotlinx.html.*

class CreateSourceView : AppView {

    fun render(html: HTML) {
        html.apply {
            header(this, "Sources")
            body {
                h1 { text("Sources") }
                createSourceForm()
            }
        }
    }

    fun BODY.createSourceForm() {
        form(action = "/asciilab/admin/sources/new", method = FormMethod.post) {
            fieldSet {
                label { text("Source Name") }
                input(InputType.text, name = "name")
            }

            fieldSet {
                label { text("Source URL") }
                input(InputType.text, name = "url")
            }

            input(InputType.submit) {
                value = "Save"
            }
            a("/asciidoctor/admin/sources") { text("Cancel") }
        }
    }

}