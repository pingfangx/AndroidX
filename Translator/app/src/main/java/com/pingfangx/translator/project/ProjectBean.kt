package com.pingfangx.translator.project

import android.databinding.ObservableField

/**
 * 项目bean
 *
 * @author pingfangx
 * @date 2017/9/10
 */
class ProjectBean(name: String, path: String) {
    val name: ObservableField<String> = ObservableField(name)
    val path: ObservableField<String> = ObservableField(path)

}