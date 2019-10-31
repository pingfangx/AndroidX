package com.pingfangx.demo.androidx.activity.com.google.android.material.circularreveal

import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealWidget
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * @author pingfangx
 * @date 2019/6/12
 */
class CircularRevealLinearLayoutDemo : ActivityInitializer {
    var view: View? = null
    var circularRevealCompat: CircularRevealWidget? = null
    var cx = 0F
    var cy = 0F
    var startRadius = 0F
    var endRadius = 0F
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)

        activity.findViewById<View>(R.id.btn_1)?.setOnClickListener { v ->
            initValue(activity)
            val animator = CircularRevealCompat.createCircularReveal(circularRevealCompat, cx, cy, startRadius, endRadius)
            animator.start()
        }

        activity.findViewById<View>(R.id.btn_2).setOnClickListener { v ->
            initValue(activity)
            val animator = ObjectAnimator.ofObject<CircularRevealWidget, CircularRevealWidget.RevealInfo>(
                    circularRevealCompat,
                    CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL,
                    CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL,
                    CircularRevealWidget.RevealInfo(cx, cy, startRadius),
                    CircularRevealWidget.RevealInfo(cx, cy, endRadius))
            circularRevealCompat?.let {
                animator.addListener(CircularRevealCompat.createCircularRevealListener(circularRevealCompat))
            }
            animator.duration = 2000
            animator.start()
        }

        activity.findViewById<View>(R.id.btn_3).setOnClickListener { v ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                initValue(activity)
                val animator = ViewAnimationUtils.createCircularReveal(view, cx.toInt(), cy.toInt(), startRadius, endRadius)
                animator.start()
            }
        }
    }

    private fun initValue(activity: BaseActivity) {
        view = activity.findViewById(R.id.circular_reveal_widget)
        circularRevealCompat = view as CircularRevealWidget
        cx = view!!.width / 2F
        cy = view!!.height / 2F
        startRadius = 0F
        endRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
    }
}