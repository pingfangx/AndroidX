// 仅用于断点学习源码
@file:Suppress("RedundantOverride", "UseExpressionBody")

package com.pingfangx.demo.androidx.activity.android.view.event

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.xxlog

/**
 * 事件分发
 *
 * @author pingfangx
 * @date 2019/10/31
 */
class KeyEventDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        val viewGroup = KeyEventViewGroup(activity)
        viewGroup.setBackgroundColor(Color.BLUE)

        val view = KeyEventView(activity)
        view.setBackgroundColor(Color.GREEN)
        view.setOnClickListener {
            "onClick".xxlog()
        }

        viewGroup.addView(view)
        activity.setContentView(viewGroup)
    }
}

class KeyEventViewGroup : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")// 仅用于断点学习源码
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}

class KeyEventView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")// 仅用于断点学习源码
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}