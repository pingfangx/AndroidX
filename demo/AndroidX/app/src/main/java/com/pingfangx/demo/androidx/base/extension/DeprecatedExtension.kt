package com.pingfangx.demo.androidx.base.extension

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View

/**
 * 弃用的方法
 *
 * @author pingfangx
 * @date 2018/12/26
 */
fun setBackgroundDrawable(view: View, drawable: Drawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        view.background = drawable
    } else {
        @Suppress("DEPRECATION")
        view.setBackgroundDrawable(drawable)
    }
}