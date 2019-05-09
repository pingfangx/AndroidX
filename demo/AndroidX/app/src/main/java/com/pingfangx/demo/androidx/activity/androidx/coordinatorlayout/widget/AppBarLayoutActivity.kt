package com.pingfangx.demo.androidx.activity.androidx.coordinatorlayout.widget

import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import org.jetbrains.anko.toast

/**
 *
 *
 * @author pingfangx
 * @date 2019/5/13
 */
class AppBarLayoutActivity : CoordinatorLayoutActivity() {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, R.id.common_menu, 0, "choose")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.common_menu) {
            toast("点击")
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}