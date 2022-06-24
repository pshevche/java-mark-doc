package com.github.pshevche.javamarkdoc

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.psi.javadoc.PsiDocComment
import java.awt.event.MouseEvent

class JavaMarkDocLineMarkerHandler : GutterIconNavigationHandler<PsiDocComment> {

    override fun navigate(event: MouseEvent?, element: PsiDocComment?) {
        if (element != null) {
            val commentEditor = JavaMarkDocDialogEditor(element.text)
            if (commentEditor.showAndGet()) {
                replaceCommentContent(element, commentEditor.getUpdatedComment())
            }
        }
    }

    private fun replaceCommentContent(element: PsiDocComment, newCommentContent: String) {
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