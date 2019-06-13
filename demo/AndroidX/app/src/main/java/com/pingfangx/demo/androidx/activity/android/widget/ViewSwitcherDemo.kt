package com.pingfangx.demo.androidx.activity.android.widget

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageSwitcher
import android.widget.TextSwitcher
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 切换示例
 *
 * @author pingfangx
 * @date 2019/6/13
 */
class ViewSwitcherDemo : ActivityInitializer {
    private var index = 0
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val textSwitcher: TextSwitcher = activity.findViewById(R.id.textSwitcher)

        textSwitcher.addView(TextView(activity))
        textSwitcher.addView(TextView(activity))
        textSwitcher.setOnClickListener { textSwitcher.setText((++index).toString()) }
        val inAnimation = TranslateAnimation(0, 0F, 0, 0F, Animation.RELATIVE_TO_SELF, 1F, Animation.RELATIVE_TO_SELF, 0F)
        inAnimation.duration = 400
        textSwitcher.inAnimation = inAnimation
        val outAnimation = TranslateAnimation(0, 0F, 0, 0F, Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, -1F)
        outAnimation.duration = 400
        textSwitcher.outAnimation = outAnimation


        val imageSwitcher: ImageSwitcher = activity.findViewById(R.id.imageSwitcher)
        //不需要添加 view，布局中已经有了
        imageSwitcher.inAnimation = AnimationUtils.makeInAnimation(activity, true)
        imageSwitcher.outAnimation = AnimationUtils.makeOutAnimation(activity, true)
        imageSwitcher.setOnClickListener { imageSwitcher.showNext() }
    }
}