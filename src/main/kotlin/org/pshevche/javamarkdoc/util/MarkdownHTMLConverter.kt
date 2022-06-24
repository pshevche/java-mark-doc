package org.pshevche.javamarkdoc.util

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import com.vladsch.flexmark.parser.Parser

/**
 * A small utility class abstracting away the flexmark Markdown library.
 */
class MarkdownHTMLConverter {
    companion object {

        private val MARKDOWN_PARSER = Parser.builder().build()
        private val HTML_RENDERER = HtmlRenderer.builder().build()
        private val HTML_CONVERTER = FlexmarkHtmlConverter.builder().build()

        fun convertMarkdownToHTML(markdown: String): String {
            val markdownDocument = MARKDOWN_PARSER.parse(markdown)
            return HTML_RENDERER.render(markdownDocument)
        }

        fun convertHTMLToMarkdown(html: String): String = HTML_CONVERTER.convert(html)
    }
}