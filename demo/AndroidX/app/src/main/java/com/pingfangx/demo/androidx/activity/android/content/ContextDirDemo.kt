package com.pingfangx.demo.androidx.activity.android.content

import android.app.Activity
import android.content.Context
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.xxlog

/**
 * Context 中 dir 相关的方法
 *
 * @author pingfangx
 * @date 2019/9/12
 */
class ContextDirDemo : ActivityInitializer {
    private var mActivity: Activity? = null
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        mActivity = activity
        val tvTips: TextView = activity.findViewById(R.id.tv_tips)
        tvTips.text = getTips()
    }

    private fun getTips(): String {
        val sb = StringBuilder()
        val clazz = Context::class.java
        val methods = clazz.methods
        for (method in methods) {
            val name = method.name
            if (name.endsWith("Dir") || name.endsWith("Dirs")) {
                sb.append('\n')
                        .append('\n')
                        .append(name)
                        .append('\n')
                        .append('\t')
                        .append('\t')
                val parameterTypes = method.parameterTypes
                try {
                    val result = if (parameterTypes.isEmpty()) {
                        method.invoke(mActivity)
                    } else if (parameterTypes.size == 1) {
                        method.invoke(mActivity, null)
                    } else {
                        ""
                    }
                    if (result is Array<*>) {
                        sb.append(result.joinToString("\n"))
                    } else {
                        sb.append(result)
                    }
                } catch (e: Exception) {
                    "执行方法 $name 出错".xxlog()
                }
            }
        }
        return sb.toString()
    }
}