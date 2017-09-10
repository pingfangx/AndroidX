package com.pingfangx.translator.project

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.pingfangx.translator.base.GsonDbHelper
import java.lang.reflect.Type

/**
 * 项目管理
 *
 * @author pingfangx
 * @date 2017/9/10
 */
class ProjectManager(context: Context) : GsonDbHelper<ProjectBean>(context) {
    override val mType: Type = object : TypeToken<List<ProjectBean>>() {}.type
    override val mSpKey: String = "project_list"
}