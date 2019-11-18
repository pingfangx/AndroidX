package com.pingfangx.demo.androidx.activity.view.animation

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 翻转
 *
 * @author pingfangx
 * @date 2019/8/10
 */
class RotateAnimationDemo : ActivityInitializer {
    override fun getLayoutResId(): Int = R.layout.activity_base_scroll_view

    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)

        val linearLayout = activity.findViewById<LinearLayout>(R.id.ll_container)

        val names = arrayOf(
                "rotateAnimation",
                "scaleWidth",
                "rotationX",
                "rotationY"
        )
        val onClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (v is TextView) {
                    when (v.text) {
                        "rotateAnimation" -> rotateAnimation(v)
                        "scaleWidth" -> scaleWidth(v)
                        "rotationX" -> rotationX(v)
                        "rotationY" -> rotationY(v)
                    }
                }
            }
        }
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
        layoutParams.topMargin = 30
        for (name in names) {
            val textView = TextView(activity)
            with(textView) {
                text = name
                gravity = Gravity.CENTER
                setBackgroundColor(Color.BLUE)
                setOnClickListener(onClickListener)
            }
            linearLayout.addView(textView, layoutParams)
        }
    }

    /**
     * RotateAnimation
     * 整个 view 顺时针或逆时针旋转
     */
    private fun rotateAnimation(v: View) {
        val animation = if (v.tag != true) {
            v.tag = true
            RotateAnimation(0F, 180F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        } else {
            v.tag = false
            RotateAnimation(180F, 360F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        }
        with(animation) {
            duration = 1000
            isFillEnabled = true
            fillBefore = true
            fillAfter = true
        }
        v.startAnimation(animation)
    }

    /**
     * 缩放宽度
     * 先缩小再放大，但是并不太像旋转效果
     */
    private fun scaleWidth(v: View) {
        val animation = ScaleAnimation(1F, 0F, 1F, 1F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        with(animation) {
            duration = 1000
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }

        v.startAnimation(animation)
    }

    private fun rotationX(v: View) {
        val scale = v.context.resources.displayMetrics.density
        v.cameraDistance = scale * v.height * 100
        val animator = ObjectAnimator.ofFloat(v, "rotationX", 0F, 180F)
        with(animator) {
            duration = 1000
        }
        animator.start()
    }

    private fun rotationY(v: View) {
        val scale = v.context.resources.displayMetrics.density
        v.cameraDistance = scale * v.width * 100
        val animator = ObjectAnimator.ofFloat(v, "rotationY", 0F, 180F)
        with(animator) {
            duration = 1000
        }
        animator.start()
    }
}