package com.pingfangx.demo.androidx.activity.androidx.coordinatorlayout.widget

import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_layout.*

/**
 * 抓叠标题
 *
 * @author pingfangx
 * @date 2019/5/13
 */
class CollapsingToolbarLayoutActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        toolbar.title = "标题"
        setSupportActionBar(toolbar)

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
        return R.layout.activity_collapsing_toolbar_layout
    }
}