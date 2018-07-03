package com.pingfangx.demo.androidx.activity.thirdparty

import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.MapNavigationActivity
import com.pingfangx.demo.androidx.base.ActivityBean
import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 第三方的一些库的集成
 *
 * @author pingfangx
 * @date 2018/7/18
 */
class ThirdPartyListActivity : BaseActivityListActivity() {
    override fun generateActivityList(): MutableList<ActivityBean> {
        val list = mutableListOf<ActivityBean>()
        list.add(ActivityBean(this, MapNavigationActivity::class.java))
        return list
    }
}