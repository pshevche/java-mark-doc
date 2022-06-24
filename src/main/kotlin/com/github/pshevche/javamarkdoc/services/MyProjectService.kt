package com.github.pshevche.javamarkdoc.services

import com.intellij.openapi.project.Project
import com.github.pshevche.javamarkdoc.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
