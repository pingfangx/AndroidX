package com.pingfangx.demo.androidx.activity.android.gesture

import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.view.View
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * 手势
 * 源码可查看 https://blog.csdn.net/stevenhu_223/article/details/9394491
 *
 * @author pingfangx
 * @date 2019/6/13
 */
class GestureOverlayViewDemo : ActivityInitializer {
    companion object {
        const val MODE_SAVE = 1
        const val MODE_RECOGNIZE = 2
    }

    private var mode: Int = 0
    private var count = 0
    private var tv: TextView? = null
    private val gestureLibrary: GestureLibrary by lazy {
        GestureLibraries.fromFile("")
    }

    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        tv = activity.findViewById(R.id.tv_1)
        val gestureOverlayView = activity.findViewById<GestureOverlayView>(R.id.gestureOverlayView)
        gestureOverlayView.addOnGesturePerformedListener { overlay, gesture ->
            when (mode) {
                MODE_SAVE -> {
                    val name = "手势${++count}"
                    gestureLibrary.addGesture(name, gesture)
                    overlay.context.toast("添加手势 [$name]")
                }
                MODE_RECOGNIZE -> {
                    val recognize = gestureLibrary.recognize(gesture)
                    tv?.text = recognize.joinToString(separator = "\n") { "${it.name}:${it.score}" }
                    recognize.forEach {
                        if (it.score > 2) {
                            overlay.context.toast("识别出手势 [${it.name}]")
                        }
                    }
                }
                else -> overlay.context.toast("手势绘制完成")
            }
        }
        activity.findViewById<View>(R.id.btn_1).setOnClickListener { v ->
            mode = MODE_SAVE
            val message = "绘制手势来添加"
            v.context.toast(message)
            tv?.text = message
        }
        activity.findViewById<View>(R.id.btn_2).setOnClickListener { v ->
            mode = MODE_RECOGNIZE
            val message = "绘制手势来识别"
            v.context.toast(message)
            tv?.text = message
        }
    }
}