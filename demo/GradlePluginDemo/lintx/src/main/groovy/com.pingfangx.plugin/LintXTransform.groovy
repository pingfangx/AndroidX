package com.pingfangx.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

class LintXTransform extends Transform {
    Project project;
    int level

    LintXTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return 'LintXTransform'
    }

    /**
     * 需要处理的数据类型，CONTENT_CLASS代表处理class文件
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指定Transform的作用范围
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 指明当前Transform是否支持增量编译
     */
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        //每个输入
        transformInvocation.inputs.each { TransformInput input ->
            //每个文件夹
            input.directoryInputs.each { DirectoryInput directoryInput ->
                LintX.processDir(project, level, directoryInput.file)
                // 获取output目录
                //def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                // 将input的目录复制到output指定目录
                //FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }
}