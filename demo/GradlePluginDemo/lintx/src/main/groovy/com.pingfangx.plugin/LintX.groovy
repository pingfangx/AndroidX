package com.pingfangx.plugin

import org.gradle.api.Project

class LintX {
    /**
     * 处理目录
     */
    static void processDir(Project project, int level, File dirFile) {
        println("处理文件，级别为 $level ，目录为 ${dirFile.absolutePath}")
        if (dirFile.isDirectory()) {
            dirFile.eachFileRecurse { File file ->
                println("处理文件 $file")
            }
        }
    }
}