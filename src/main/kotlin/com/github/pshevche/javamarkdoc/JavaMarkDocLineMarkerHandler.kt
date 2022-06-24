package com.github.pshevche.javamarkdoc

import com.github.pshevche.javamarkdoc.MarkdownHTMLConverter.Companion.htmlToMarkdown
import com.github.pshevche.javamarkdoc.MarkdownHTMLConverter.Companion.markdownToHTML
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.psi.javadoc.PsiDocComment
import java.awt.event.MouseEvent

class JavaMarkDocLineMarkerHandler : GutterIconNavigationHandler<PsiDocComment> {

    override fun navigate(event: MouseEvent?, element: PsiDocComment?) {
        if (element != null) {
            val documentationContentAsHtml = extractDocumentationContent(element.text)
            val documentationContentAsMarkdown = htmlToMarkdown(documentationContentAsHtml)
            val commentEditor = JavaMarkDocDialogEditor(element.project, documentationContentAsMarkdown)
            if (commentEditor.showAndGet()) {
                val newDocumentationContentAsHtml = markdownToHTML(commentEditor.getUpdatedDocumentationContent())
                val newJavadocComment = wrapDocumentationContentIntoJavadoc(newDocumentationContentAsHtml)
                updateJavadocComment(element, newJavadocComment)
            }
        }
    }

    private fun extractDocumentationContent(javadocComment: String): String {
        val lines = javadocComment.split("\n")
        return lines.subList(1, lines.size - 1).joinToString("\n") { it.replace(" * ", "") }
    }

    private fun wrapDocumentationContentIntoJavadoc(documentationContent: String): String {
        val lines = documentationContent.split("\n")
        val prefix = "/**\n"
        val suffix = "\n */"
        return prefix + lines.joinToString("\n") { " * $it" } + suffix
    }

    private fun updateJavadocComment(element: PsiDocComment, newCommentContent: String) {
        val project = element.project
        val textEditor = FileEditorManager.getInstance(project).selectedEditor as TextEditor
        val document = textEditor.editor.document
        val textRange = element.textRange

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(
                textRange.startOffset,
                textRange.endOffset,
                newCommentContent
            )
        }
    }
}