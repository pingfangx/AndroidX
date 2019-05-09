package com.pingfangx.demo.androidx.activity.androidx.coordinatorlayout.widget

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.common.MultipleLayoutDemoActivity

/**
 * Coordinator
 * https://www.jianshu.com/p/4a77ae4cd82f
 *
 * @author pingfangx
 * @date 2019/5/9
 */
open class CoordinatorLayoutActivity : MultipleLayoutDemoActivity() {
    override fun initViews() {
        super.initViews()
        addLayoutInfo(R.layout.activity_coordinator_layout_relative_layout, "RelativeLayout:FloatingActionButton 和 Snackbar")
        addLayoutInfo(R.layout.activity_coordinator_layout_snackbar, "CoordinatorLayout:FloatingActionButton 和 Snackbar")
        addLayoutInfo(LayoutInfo(R.layout.activity_app_bar_layout, "AppBarLayout", AppBarLayoutActivity::class.java))
    }

    fun onClickFloatingActionButton(view: View) {
        Snackbar.make(findViewById(R.id.container), "SnackBar", Snackbar.LENGTH_LONG).show()
    }
}