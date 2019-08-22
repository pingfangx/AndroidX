package com.pingfangx.demo.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import org.jetbrains.anko.toast

/**
 * 学习
 *
 * @author pingfangx
 * @date 2019/8/22
 */
class MyAccessibilityService : AccessibilityService() {
    companion object {
        const val TAG = "MyAccessibilityService"
    }

    private lateinit var mFloatWindowManager: FloatWindowManager
    override fun onServiceConnected() {
        super.onServiceConnected()
        mFloatWindowManager = FloatWindowManager(this)
        mFloatWindowManager.show()
        toast("onServiceConnected")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        toast("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        mFloatWindowManager.hide()
        super.onDestroy()
    }

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) {
            return
        }
        Log.d(TAG, "onAccessibilityEvent" + event.eventType)
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val node = event.source
            if (node.text == "开始") {
                toast("点击了开始")
                val button = findViewById("button_click_demo")
                button?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    private fun findViewById(id: String): AccessibilityNodeInfo? {
        val viewId = if (id.startsWith(packageName).not()) {
            "$packageName:id/$id"
        } else {
            id
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (rootInActiveWindow == null) {
                return null
            }
            val views = rootInActiveWindow.findAccessibilityNodeInfosByViewId(viewId)
            if (views != null) {
                return views[0]
            }
        }
        return null
    }
}