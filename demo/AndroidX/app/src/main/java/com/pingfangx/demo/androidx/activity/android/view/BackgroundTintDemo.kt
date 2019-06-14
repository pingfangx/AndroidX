package com.pingfangx.demo.androidx.activity.android.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 着色
 * 原理可查看 https://yifeng.studio/2017/03/30/android-tint/
 *
 * @author pingfangx
 * @date 2019/6/14
 */
class BackgroundTintDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val linearLayout = activity.findViewById<LinearLayout>(R.id.ll_root)

        val arrayOfModes = PorterDuff.Mode.values()
        for (mode in arrayOfModes) {
            val ll = LinearLayout(activity)
            ll.orientation = LinearLayout.HORIZONTAL

            val tv = TextView(activity)
            tv.text = mode.name
            ll.addView(tv, 300, ViewGroup.LayoutParams.WRAP_CONTENT)

            val imageView = ImageView(activity)
            imageView.setImageResource(R.mipmap.ic_launcher)
            ll.addView(imageView)

            val button = Button(activity)
            button.setBackgroundResource(R.mipmap.ic_launcher)
            button.text = "background"
            ll.addView(button)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.imageTintMode = mode
                imageView.imageTintList = ColorStateList.valueOf(Color.RED)
                button.backgroundTintMode = mode
                button.backgroundTintList = ColorStateList.valueOf(Color.RED)
            }

            linearLayout.addView(ll)
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_base_scroll_view
}