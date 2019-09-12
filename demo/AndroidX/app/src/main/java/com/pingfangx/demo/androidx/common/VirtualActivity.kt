package com.pingfangx.demo.androidx.common

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
    var activityBaseName = ""
    var activityInitializer: ActivityInitializer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val name = intent.getStringExtra(INTENT_EXTRA_COMMON)
        activityBaseName = if (name.isNullOrEmpty()) {
            ""
        } else {
            activityInitializer = createActivityInitializer(name)
            name.trim().split(".").last().removeSuffix("Demo")
        }
        super.onCreate(savedInstanceState)
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
}