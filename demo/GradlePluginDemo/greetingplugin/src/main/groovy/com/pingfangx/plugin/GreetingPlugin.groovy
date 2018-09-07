package com.pingfangx.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 插件
 */
class GreetingPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        //应用该插件后，会创建一个任务，名为 'hello'
        //可在 Gradle 中看到并执行
        //执行后调用 GreetingTask 中的 Action
        project.task('hello', type: GreetingTask)
    }
}