package org.pshevche.javamarkdoc.ui

import com.intellij.lang.Language
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.EditorTextField
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * A dialog containing a Markdown editor.
 */
class MarkdownEditorDialog(project: Project, initialComment: String) : DialogWrapper(true) {
    private val editorField: EditorTextField

    companion object {
        val MARKDOWN = Language.findLanguageByID("Markdown")!!.associatedFileType
    }

    init {
        title = "Edit Javadoc as Markdown"
        editorField = EditorTextField(
            EditorFactory.getInstance().createDocument(StringUtil.convertLineSeparators(initialComment)),
            project,
            MARKDOWN,
            false,
            false
        )
        init()
    }

    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())
        editorField.preferredSize = Dimension(500, 500)
        dialogPanel.add(editorField, BorderLayout.CENTER)
        return dialogPanel
    }

    fun getUpdatedContent(): String {
        return editorField.text
    }
}