package com.pingfangx.demo.androidx.activity.view.material

import android.view.View
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_drawer_layout.*

/**
 * 抽屉式布局
 *
 * @author pingfangx
 * @date 2018/8/2
 */
class DrawerLayoutActivity : BaseActivity() {
    fun onClickTvShowDrawer(view: View) {
        val drawer: View? = when (view.id) {
            R.id.tv_1 -> tv_drawer_1
            R.id.tv_2 -> tv_drawer_2
            else -> null
        }
        drawer?.let {
            if (drawer_layout.isDrawerOpen(drawer)) {
                drawer_layout.closeDrawer(drawer)
            } else {
                drawer_layout.openDrawer(drawer)
            }
        }
    }
}