package com.pingfangx.demo.accessibility

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.ViewCompat
import org.jetbrains.anko.toast

/**
 * 浮窗
 *
 * @author pingfangx
 * @date 2019/8/22
 */
class FloatWindowManager(val context: Context) {
    private val mWindowManager: WindowManager by lazy {
        context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
    private val mLayoutParams: WindowManager.LayoutParams by lazy {
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        // 设置显示的模式
        lp.format = PixelFormat.RGBA_8888
        lp.gravity = Gravity.TOP or Gravity.LEFT
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.y = 500
        lp
    }
    //悬浮窗
    private val mFloatWindowView: TextView by lazy {
        val textView = TextView(context)
        textView.setBackgroundColor(Color.RED)
        textView
    }

    /**
     * 显示
     */
    fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                context.toast("需要浮窗授权")
                context.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName)))
                return
            }
        }
        if (ViewCompat.isAttachedToWindow(mFloatWindowView).not()) {
            mWindowManager.addView(mFloatWindowView, mLayoutParams)
        }
        mFloatWindowView.text = "running"
    }

    /**
     * 隐藏
     */
    fun hide() {
        if (ViewCompat.isAttachedToWindow(mFloatWindowView)) {
            mWindowManager.removeView(mFloatWindowView)
        }
    }
}