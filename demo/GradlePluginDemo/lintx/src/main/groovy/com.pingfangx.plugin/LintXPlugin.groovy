package com.pingfangx.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LintXPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        //AppExtension对应build.gradle中android{...}
        def android = project.extensions.getByType(AppExtension)
        //注册 transform
        def lintTransform = new LintXTransform(project)
        android.registerTransform(lintTransform)

        // 通过Extension的方式传递将要被注入的自定义代码
        def extension = project.extensions.create('lintx', LintXExtension)
        project.afterEvaluate {
            lintTransform.level = extension.level
        }
    }
}