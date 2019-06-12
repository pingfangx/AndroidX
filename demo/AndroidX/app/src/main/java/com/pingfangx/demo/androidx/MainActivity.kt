package com.pingfangx.demo.androidx

import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 一些简单的测试，直接集合到一个 app 里
 */
class MainActivity : BaseActivityListActivity() {
    override fun initActivityList() {
        super.initActivityList()
        addVirtualActivity("com.google.android.material.circularreveal.CircularRevealLinearLayout")
    }
}
