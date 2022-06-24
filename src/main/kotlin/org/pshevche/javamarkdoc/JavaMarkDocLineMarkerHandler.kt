package org.pshevche.javamarkdoc

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.psi.javadoc.PsiDocComment
import org.pshevche.javamarkdoc.ui.MarkdownEditorDialog
import org.pshevche.javamarkdoc.util.JavadocFormatter.Companion.extractDocumentationContentFromComment
import org.pshevche.javamarkdoc.util.JavadocFormatter.Companion.wrapDocumentationContentIntoComment
import org.pshevche.javamarkdoc.util.MarkdownHTMLConverter.Companion.convertHTMLToMarkdown
import org.pshevche.javamarkdoc.util.MarkdownHTMLConverter.Companion.convertMarkdownToHTML
import java.awt.event.MouseEvent

/**
 * Handles click events fired by {@link JavaMarkDocLineMarkerProvider}. On click, opens a dialog containing an editor
 * displaying the content of the current comment as Markdown. The edited Markdown content is transformed back to Javadoc HTML
 * and original content is replaced.
 */
class JavaMarkDocLineMarkerHandler : GutterIconNavigationHandler<PsiDocComment> {

    override fun navigate(event: MouseEvent?, element: PsiDocComment?) {
        if (element != null) {
            val documentationContentAsHtml = extractDocumentationContentFromComment(element.text)
            val documentationContentAsMarkdown = convertHTMLToMarkdown(documentationContentAsHtml)

            val commentEditor = MarkdownEditorDialog(element.project, documentationContentAsMarkdown)
            if (commentEditor.showAndGet()) {
                val newDocumentationContentAsHtml = convertMarkdownToHTML(commentEditor.getUpdatedContent())
                val newJavadocComment = wrapDocumentationContentIntoComment(newDocumentationContentAsHtml)

                updateJavadocComment(element, newJavadocComment)
            }
        }
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