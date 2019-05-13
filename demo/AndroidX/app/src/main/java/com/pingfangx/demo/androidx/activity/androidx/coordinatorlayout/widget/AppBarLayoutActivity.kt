package com.pingfangx.demo.androidx.activity.androidx.coordinatorlayout.widget

import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_app_bar_layout.*

/**
 *
 *
 * @author pingfangx
 * @date 2019/5/13
 */
class AppBarLayoutActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        val llContainer = findViewById<LinearLayout>(R.id.ll_container)
        llContainer?.let {
            for (i in 0..100) {
                val textView = TextView(this)
                textView.text = i.toString()
                llContainer.addView(textView)
            }
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_app_bar_layout
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL, 0, "noScroll\n不滚动")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL, 0, "scroll\n滚动相关，滑到顶才开始进入")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED, 0, "scroll|exitUntilCollapsed\n退出时先折叠到最小高度")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS, 0, "scroll|enterAlways\n每次下滑都进入")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED, 0, "scroll|enterAlways|enterAlwaysCollapsed\n每次下滑都进入到最小高度")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP, 0, "scroll|snap\n自动补充为离开(25%)或显示(75%)")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS, 0, "scroll|snap|snapMargins\n" +
                "在完全展示时，会展示包括 margin 的部分（如果不设置只展示 view 部分，继续下划才展示 maring）" +
                "相应的在计算时也要考虑 view 本身的 marginTop\n具体来说，比如 View 高 100，marginTop 100。默认情况 75% 只要 view 展示部分大小 75 即可，考虑 margin 则需要大于 150 才行。")
        menu.add(0, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP, 0, "scroll|enterAlways|snap\nenterAlways 与 snap 组合使用")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        tv_title.text = item.title
        val layoutParams = tv_title.layoutParams
        if (layoutParams is AppBarLayout.LayoutParams) {
            layoutParams.scrollFlags = item.itemId
            tv_title.layoutParams = layoutParams
        }
        return true
    }
}