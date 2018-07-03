package com.pingfangx.demo.androidx

import com.pingfangx.demo.androidx.activity.view.textview.TextViewBoldActivity
import com.pingfangx.demo.androidx.base.ActivityBean
import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 一些简单的测试，直接集合到一个 app 里
 */
class MainActivity : BaseActivityListActivity() {
    override fun generateActivityList(): MutableList<ActivityBean> {
        val list = mutableListOf<ActivityBean>()
        list.add(ActivityBean(this, TextViewBoldActivity::class.java))
        return list
    }
}
