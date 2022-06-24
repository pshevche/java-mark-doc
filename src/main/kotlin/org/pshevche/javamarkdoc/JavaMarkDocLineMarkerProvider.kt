package org.pshevche.javamarkdoc

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.javadoc.PsiDocComment

/**
 * Adds a gutter icon to Javadoc comments triggering a {@link JavaMarkDocLineMarkerHandler}
 * that allows editing Javadoc comments using Markdown.
 */
class JavaMarkDocLineMarkerProvider : LineMarkerProvider {
    companion object {
        const val TOOLTIP_TEXT = "Edit comment using Markdown"
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element is PsiDocComment) {
            return LineMarkerInfo(
                element,
                element.textRange,
                AllIcons.Actions.EditScheme,
                { TOOLTIP_TEXT },
                JavaMarkDocLineMarkerHandler(),
                GutterIconRenderer.Alignment.LEFT,
                { TOOLTIP_TEXT }
            )
        }

        return null
    }
}