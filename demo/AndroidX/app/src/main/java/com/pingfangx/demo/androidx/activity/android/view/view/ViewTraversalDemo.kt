package com.pingfangx.demo.androidx.activity.android.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 *
 * @author pingfangx
 * @date 2019/10/31
 */
class ViewTraversalDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        val viewGroup: ViewGroup = activity.findViewById(R.id.ll_container)
        TraversalDemoView(activity).also {
            it.setBackgroundColor(Color.BLUE)
            viewGroup.addView(it)
        }
    }
}

class TraversalDemoView : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}