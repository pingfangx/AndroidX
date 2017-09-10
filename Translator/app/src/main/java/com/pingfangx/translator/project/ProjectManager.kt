package com.pingfangx.translator.project

import android.content.Context
import com.pingfangx.translator.base.GsonDbHelper

/**
 * 项目管理
 *
 * @author pingfangx
 * @date 2017/9/10
 */
class ProjectManager(context: Context) : GsonDbHelper<ProjectBean>(context) {
    override val mSpKey: String = "project_list"
}