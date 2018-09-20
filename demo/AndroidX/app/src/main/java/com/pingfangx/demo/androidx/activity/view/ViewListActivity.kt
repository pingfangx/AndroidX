package com.pingfangx.demo.androidx.activity.view

import com.pingfangx.demo.androidx.activity.view.imageview.ImageViewScaleTypeActivity
import com.pingfangx.demo.androidx.activity.view.listview.ListViewActivity
import com.pingfangx.demo.androidx.activity.view.material.DrawerLayoutActivity
import com.pingfangx.demo.androidx.activity.view.textview.TextViewBoldActivity
import com.pingfangx.demo.androidx.activity.view.textview.TextViewSkewActivity
import com.pingfangx.demo.androidx.base.ActivityBean
import com.pingfangx.demo.androidx.base.BaseActivityListActivity

/**
 * 与 View 相关的列表
 *
 * @author pingfangx
 * @date 2018/7/18
 */
class ViewListActivity : BaseActivityListActivity() {
    override fun generateActivityList(): MutableList<ActivityBean> {
        val list = mutableListOf<ActivityBean>()
        list.add(ActivityBean(this, DrawerLayoutActivity::class.java))
        list.add(ActivityBean(this, ImageViewScaleTypeActivity::class.java))
        list.add(ActivityBean(this, ListViewActivity::class.java))
        list.add(ActivityBean(this, TextViewBoldActivity::class.java))
        list.add(ActivityBean(this, TextViewSkewActivity::class.java))
        return list
    }
}