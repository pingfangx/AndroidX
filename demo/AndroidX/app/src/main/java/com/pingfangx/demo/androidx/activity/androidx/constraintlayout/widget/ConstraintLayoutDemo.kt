package com.pingfangx.demo.androidx.activity.androidx.constraintlayout.widget

import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 测试动态设置约束
 *
 * @author pingfangx
 * @date 2019/11/17
 */
class ConstraintLayoutDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val context = activity
        var id = 0
        val constraintLayout = ConstraintLayout(context)
        activity.setContentView(constraintLayout)

        run {
            val tv = TextView(context)
            tv.text = "LayoutParams"
            tv.id = ++id
            tv.setBackgroundColor(Color.GREEN)
            tv.setOnClickListener { v ->
                val layoutParams = v.layoutParams
                if (layoutParams is ConstraintLayout.LayoutParams) {
                    layoutParams.rightToRight = if (layoutParams.rightToRight == ConstraintLayout.LayoutParams.PARENT_ID) {
                        -1
                    } else {
                        ConstraintLayout.LayoutParams.PARENT_ID
                    }
                    v.layoutParams = layoutParams
                }
            }

            val layoutParams = ConstraintLayout.LayoutParams(300, 200)
            constraintLayout.addView(tv, layoutParams)
        }
        run {
            val tv = TextView(context)
            tv.text = "ConstraintSet"
            tv.id = ++id
            tv.setBackgroundColor(Color.BLUE)
            tv.setOnClickListener { v ->
                val layoutParams = v.layoutParams
                if (layoutParams is ConstraintLayout.LayoutParams) {
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    if (layoutParams.rightToRight == ConstraintLayout.LayoutParams.PARENT_ID) {
                        constraintSet.connect(v.id, ConstraintSet.RIGHT, ConstraintSet.UNSET, ConstraintSet.RIGHT)
                    } else {
                        constraintSet.connect(v.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
                    }
                    constraintSet.applyTo(constraintLayout)
                }
            }

            val layoutParams = ConstraintLayout.LayoutParams(300, 200)
            layoutParams.topMargin = 200
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            constraintLayout.addView(tv, layoutParams)
        }

    }
}