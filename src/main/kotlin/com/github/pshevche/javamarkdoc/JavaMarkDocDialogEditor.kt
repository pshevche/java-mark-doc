package com.github.pshevche.javamarkdoc

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel


class JavaMarkDocDialogEditor(initialComment: String) : DialogWrapper(true) {
    private val editorField: EditorTextField

    init {
        title = "Edit Javadoc as Markdown"
        editorField = EditorTextField(initialComment)
        init()
    }

    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())
        editorField.preferredSize = Dimension(500, 500)
        dialogPanel.add(editorField, BorderLayout.CENTER)
        return dialogPanel
    }

    fun getUpdatedDocumentationContent(): String {
        return editorField.text
    }
}