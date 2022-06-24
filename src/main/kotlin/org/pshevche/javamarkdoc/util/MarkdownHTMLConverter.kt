package org.pshevche.javamarkdoc.util

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import com.vladsch.flexmark.parser.Parser

class MarkdownHTMLConverter {
    companion object {
        fun markdownToHTML(markdown: String): String {
            val parser = Parser.builder().build()
            val document = parser.parse(markdown)
            val htmlRenderer = HtmlRenderer.builder().build()
            return htmlRenderer.render(document)
        }

        fun htmlToMarkdown(html: String): String = FlexmarkHtmlConverter.builder().build().convert(html)
    }
}