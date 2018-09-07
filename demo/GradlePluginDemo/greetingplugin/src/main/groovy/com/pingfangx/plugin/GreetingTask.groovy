package com.pingfangx.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 任务
 */
class GreetingTask extends DefaultTask {
    /**
     * 默认值，可根据外部的设置而变化
     */
    String greeting = 'hello from GreetingTask'

    /**
     * 注解为操作
     */
    @TaskAction
    def greet() {
        println(greeting)
    }
}