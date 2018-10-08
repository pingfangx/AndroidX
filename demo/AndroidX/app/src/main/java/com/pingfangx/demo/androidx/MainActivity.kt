package com.pingfangx.demo.androidx

import com.pingfangx.demo.androidx.activity.thirdparty.ThirdPartyListActivity
import com.pingfangx.demo.androidx.activity.tool.ToolListActivity
import com.pingfangx.demo.androidx.activity.view.ViewListActivity
import com.pingfangx.demo.androidx.base.ActivityBean
import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 一些简单的测试，直接集合到一个 app 里
 */
class MainActivity : BaseActivityListActivity() {
    override fun generateActivityList(): MutableList<ActivityBean> {
        val list = mutableListOf<ActivityBean>()
        list.add(ActivityBean(this, ThirdPartyListActivity::class.java))
        list.add(ActivityBean(this, ViewListActivity::class.java))
        list.add(ActivityBean(this, ToolListActivity::class.java))
        return list
    }
}
