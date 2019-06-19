package com.pingfangx.demo.androidx.activity.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


/**
 * 浮窗
 *
 * @author pingfangx
 * @date 2019/6/19
 */
class FloatWindowDemo : ActivityInitializer {
    private lateinit var mActivity: BaseActivity

    private var run: Boolean = false
    /**
     * 仅是示例浮窗使用，实际应该使用 Service 或者别的可以再考虑
     */
    private val runnable = Runnable {
        while (run) {
            mFloatWindowView.post {
                mFloatWindowView.text = format.format(Date(System.currentTimeMillis()))
            }
            Thread.sleep(1)
        }
    }

    private val mWindowManager: WindowManager by lazy {
        mActivity.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
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
    private val format: SimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    }

    //悬浮窗
    private val mFloatWindowView: TextView by lazy {
        val textView = TextView(mActivity)
        textView.setBackgroundColor(Color.RED)
        textView
    }

    override fun initActivity(activity: BaseActivity) {
        mActivity = activity
        super.initActivity(activity)
        val llRoot = activity.findViewById<LinearLayout>(R.id.ll_root)
        if (llRoot != null) {
            val btnShow = Button(activity)
            btnShow.text = "show"
            btnShow.setOnClickListener { showFloatWindow() }
            llRoot.addView(btnShow)

            val btnHide = Button(activity)
            btnHide.text = "hide"
            btnHide.setOnClickListener { hideFloatWindow() }
            llRoot.addView(btnHide)

            val btnStart = Button(activity)
            btnStart.text = "start"
            btnStart.setOnClickListener { start() }
            llRoot.addView(btnStart)

            val btnStop = Button(activity)
            btnStop.text = "stop"
            btnStop.setOnClickListener { stop() }
            llRoot.addView(btnStop)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_base_tips
    }

    /**
     * 显示
     */
    private fun showFloatWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(mActivity)) {
                mActivity.toast("需要浮窗授权")
                mActivity.startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mActivity.packageName)), 0)
                return
            }
        }
        if (ViewCompat.isAttachedToWindow(mFloatWindowView).not()) {
            mWindowManager.addView(mFloatWindowView, mLayoutParams)
        }
        start()
    }

    /**
     * 隐藏
     */
    private fun hideFloatWindow() {
        if (ViewCompat.isAttachedToWindow(mFloatWindowView)) {
            mWindowManager.removeView(mFloatWindowView)
        }
        stop()
    }

    private fun start() {
        stop()
        //不正确的实现，用于等待之前的停止
        Thread.sleep(2)
        run = true
        Thread(runnable).start()
    }

    private fun stop() {
        run = false
    }
}