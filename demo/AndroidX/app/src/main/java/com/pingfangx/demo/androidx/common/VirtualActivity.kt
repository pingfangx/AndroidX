package com.pingfangx.demo.androidx.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.*
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_COMMON

/**
 * 虚拟的 Activity
 *
 * @author pingfangx
 * @date 2019/6/12
 */
class VirtualActivity : BaseActivity() {
    companion object {
        fun createInent(context: Context, name: String) = Intent(context, VirtualActivity::class.java).putExtra(INTENT_EXTRA_COMMON, name)
    }

    var activityBaseName = ""
    var activityInitializer: ActivityInitializer? = null
    var activityLifecycle: ActivityLifecycle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val name = intent.getStringExtra(INTENT_EXTRA_COMMON)
        activityBaseName = if (name.isNullOrEmpty()) {
            ""
        } else {
            activityInitializer = createActivityInitializer(name)
            if (activityInitializer is ActivityLifecycle) {
                activityLifecycle = activityInitializer as ActivityLifecycle
            }
            name.trim().split(".").last().removeSuffix("Demo")
        }
        super.onCreate(savedInstanceState)
        activityLifecycle?.onCreate(this, savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        activityInitializer?.initActivity(this)
    }

    private fun createActivityInitializer(name: String): ActivityInitializer? {
        try {
            val clazz = Class.forName(name)
            return clazz.newInstance() as ActivityInitializer
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getLayoutResIdFromResources(): Int {
        val layoutResId = activityInitializer?.getLayoutResId()
        return if (layoutResId != null && layoutResId > 0) {
            layoutResId
        } else {
            val activityLayoutResId = getIdentifier(activityBaseName.camelToUnderline(), "layout", "activity")
            if (activityLayoutResId != 0) {
                activityLayoutResId
            } else {
                R.layout.activity_base_tips
            }
        }
    }

    override fun getTitleResIdFromResources() = getTitleFromRes(activityBaseName)
    override fun getTipsResIdFromResources() = getIdentifier(activityBaseName.camelToUnderline(), prefix = "tips")

    override fun onStart() {
        super.onStart()
        activityLifecycle?.onStart(this)
    }

    override fun onPause() {
        super.onPause()
        activityLifecycle?.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        activityLifecycle?.onResume(this)
    }

    override fun onStop() {
        super.onStop()
        activityLifecycle?.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityLifecycle?.onDestroy(this)
    }

    override fun onRestart() {
        super.onRestart()
        activityLifecycle?.onRestart(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        activityLifecycle?.onNewIntent(this, intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activityLifecycle?.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityLifecycle?.onActivityResult(this, requestCode, resultCode, data)
    }
}