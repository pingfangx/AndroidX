package com.pingfangx.demo.androidx.base.extension

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 应用于 BaseActivity 的扩展
 *
 * @author pingfangx
 * @date 2019/10/29
 *
 */

fun BaseActivity.addButton(text: CharSequence, listener: View.OnClickListener?) {
    val btn = Button(this)
    btn.isAllCaps = false
    btn.text = text
    btn.setOnClickListener(listener)

    val viewGroup: ViewGroup = this.findViewById(R.id.ll_container)
    viewGroup.addView(btn)
}