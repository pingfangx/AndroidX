package com.pingfangx.demo.androidx.base

import android.content.Context
import android.util.Log

/**
 * 扩展
 *
 * @author pingfangx
 * @date 2018/7/2
 */


fun Context.getTitleResId(clazz: Class<out Any>): Int {
    var name = clazz.simpleName
    name = name.removeSuffix("Activity")
    name = "title_" + name.camelToUnderline()
    return this.resources.getIdentifier(name, "string", packageName)
}

fun Context.getTitleFromRes(clazz: Class<out Any>): String {
    val titleResId = this.getTitleResId(clazz)
    if (titleResId == 0) {
        return ""
    } else {
        return try {
            this.getString(titleResId)
        } catch (e: Exception) {
            ""
        }
    }
}

/**
 * 驼峰转下划线
 */
fun String.camelToUnderline(): String {
    val stringBuilder = StringBuilder()
    for (i in 0 until this.length) {
        val c = this[i]
        if (c.isUpperCase()) {
            if (i != 0) {
                //添加下划线
                stringBuilder.append('_')
            }
            stringBuilder.append(c.toLowerCase())
        } else {
            stringBuilder.append(c)
        }
    }
    return stringBuilder.toString()
}

/**
 * 输入固定 tag 的 log
 */
fun String.xxlog() {
    Log.d("xxlog", this)
}