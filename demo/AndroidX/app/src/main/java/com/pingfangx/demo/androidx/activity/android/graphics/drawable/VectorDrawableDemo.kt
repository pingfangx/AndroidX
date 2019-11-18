package com.pingfangx.demo.androidx.activity.android.graphics.drawable

import android.graphics.drawable.Animatable
import android.view.ViewGroup
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * VectorDrawable
 *
 * @author pingfangx
 * @date 2019/11/18
 */
class VectorDrawableDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val context = activity
        val container = activity.findViewById<ViewGroup>(R.id.ll_container)
        run {
            val tv = TextView(context)
            tv.setBackgroundResource(R.drawable.demo_vector_drawable)
            container.addView(tv, 200, 200)
        }
        run {
            val tv = TextView(context)
            tv.setBackgroundResource(R.drawable.demo_animated_vector_drawable)
            tv.setOnClickListener {
                val background = it.background
                if (background is Animatable) {
                    background.start()
                }
            }
            container.addView(tv, 200, 200)
        }
    }
}