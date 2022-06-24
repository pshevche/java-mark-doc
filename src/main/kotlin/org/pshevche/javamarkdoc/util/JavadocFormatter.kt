package org.pshevche.javamarkdoc.util

/**
 * A utility class helping with extracting documentation content from the Javadoc comment.
 */
class JavadocFormatter {
    companion object {
        fun extractDocumentationContentFromComment(javadocComment: String): String {
            val lines = javadocComment.split("\n")
            return lines.subList(1, lines.size - 1).joinToString("\n") { it.replace(" * ", "") }
        }

        fun wrapDocumentationContentIntoComment(documentationContent: String): String {
            val lines = documentationContent.split("\n")
            val prefix = "/**\n"
            val suffix = "\n */"
            return prefix + lines.joinToString("\n") { " * $it" } + suffix
        }
    }
}