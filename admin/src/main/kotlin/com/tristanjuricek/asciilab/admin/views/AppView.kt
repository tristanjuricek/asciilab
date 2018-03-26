package com.tristanjuricek.asciilab.admin.views

import kotlinx.html.HTML
import kotlinx.html.head
import kotlinx.html.styleLink
import kotlinx.html.title

interface AppView {

    fun header(html: HTML, titleKey: String) {
        html.apply {
            head {
                title(titleKey)
                styleLink("/asciilab/tufte.css")
            }
        }
    }
}