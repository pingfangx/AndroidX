package com.pingfangx.demo.androidx.activity.android.graphics.drawable

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity

/**
 * 测试占用的大小
 *
 * @author pingfangx
 * @date 2019/11/18
 */
class BitmapDrawableSizeDemo : ActivityInitializer {
    companion object {
        //const val DEMO_DRAWABLE_RES_ID = R.drawable.ic_demo_100_100
        const val DEMO_DRAWABLE_RES_ID = R.drawable.ic_launcher_background
    }

    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        val dpi = Resources.getSystem().displayMetrics.densityDpi
        val qualifiers: String = if (dpi < DisplayMetrics.DENSITY_LOW) {
            "ldpi"
        } else if (dpi < DisplayMetrics.DENSITY_MEDIUM) {
            "mdpi"
        } else if (dpi < DisplayMetrics.DENSITY_HIGH) {
            "hdpi"
        } else if (dpi < DisplayMetrics.DENSITY_XHIGH) {
            "xhdpi"
        } else if (dpi < DisplayMetrics.DENSITY_XXHIGH) {
            "xxhdpi"
        } else if (dpi < DisplayMetrics.DENSITY_XXXHIGH) {
            "xxxhdpi"
        } else {
            "larger than xxxhdpi"
        }
        activity.findViewById<TextView>(R.id.tv_tips)?.text = "$dpi dpi,应该属于 $qualifiers"

        val context = activity
        val container = activity.findViewById<ViewGroup>(R.id.ll_container)

        val iv = ImageView(context)
        run {
            iv.setImageResource(R.mipmap.ic_launcher)
            container.addView(iv, 100, 100)
        }
        run {
            val btn = Button(context)
            btn.isAllCaps = false
            btn.text = "加载"
            btn.setOnClickListener {
                val drawable = it.context.resources.getDrawable(DEMO_DRAWABLE_RES_ID)
                iv.setImageDrawable(drawable)
            }
            container.addView(btn, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}