package com.pingfangx.demo.androidx.activity.android.view.animation

import android.graphics.Color
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * 补间动画
 *
 * @author pingfangx
 * @date 2019/11/17
 */
class TweenAnimationDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val context = activity
        val container = activity.findViewById<LinearLayout>(R.id.ll_container)
        container.removeAllViews()

        val tvAnimation = TextView(context)
        run {
            tvAnimation.setBackgroundColor(Color.BLUE)
            container.addView(tvAnimation, 200, 200)
            tvAnimation.setOnClickListener {
                context.toast("点击")
            }
        }
        run {
            val btn = Button(context)
            btn.isAllCaps = false
            btn.text = "Animation"
            container.addView(btn, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.setOnClickListener {
                val animation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 1F, 0, 0F, 0, 0F)
                animation.duration = 300
                animation.isFillEnabled = true
                animation.fillAfter = true
                tvAnimation.startAnimation(animation)
            }
        }

        val tvAnimator = TextView(context)
        run {
            tvAnimator.setBackgroundColor(Color.GREEN)
            container.addView(tvAnimator, 200, 200)
            tvAnimator.setOnClickListener {
                context.toast("点击")
            }
        }
        run {
            val btn = Button(context)
            btn.isAllCaps = false
            btn.text = "Animator"
            container.addView(btn, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn.setOnClickListener {
                tvAnimator.animate()
                        .translationXBy(200F)
            }
        }
    }
}