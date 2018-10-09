package com.pingfangx.demo.androidx.activity.tool

import com.pingfangx.demo.androidx.activity.tool.program.AbiAndMicroarchitectureActivity
import com.pingfangx.demo.androidx.activity.tool.program.AppListActivity
import com.pingfangx.demo.androidx.base.ActivityBean
import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 一些工具
 *
 * @author pingfangx
 * @date 2018/10/8
 */
class ToolListActivity : BaseActivityListActivity() {
    override fun generateActivityList(): MutableList<ActivityBean> {
        val list = mutableListOf<ActivityBean>()
        list.add(ActivityBean(this, AbiAndMicroarchitectureActivity::class.java))
        list.add(ActivityBean(this, AppListActivity::class.java))
        return list
    }
}