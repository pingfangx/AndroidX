package com.pingfangx.demo.androidx.base

import android.app.Activity
import android.content.Context
import android.util.Log

/**
 * 扩展
 *
 * @author pingfangx
 * @date 2018/7/2
 */

/**
 * 获取资源 id
 * @param name 名字
 * @param defType 类型，默认 string
 * @param prefix 前缀
 */
fun Context.getIdentifier(name: String = "", defType: String = "string", prefix: String = ""): Int {
    //处理名字
    var resName = if (name.isEmpty()) {
        javaClass.getUnderlineNameWithoutActivity()
    } else {
        name
    }
    //添加前缀
    if (prefix.isNotEmpty()) {
        resName = if (!prefix.endsWith("_")) {
            prefix + "_" + resName
        } else {
            prefix + resName
        }
    }
    return resources.getIdentifier(resName, defType, packageName)
}

/**
 * 获取默认的布局资源 id
 */
fun Activity.getDefaultLayoutResId(): Int {
    return getIdentifier(defType = "layout", prefix = "activity")
}

/**
 * 将 Activity 名转为下划线名，同时去掉末尾的 Activity
 */
fun Class<out Any>.getUnderlineNameWithoutActivity(): String {
    return this.simpleName.removeSuffix("Activity").camelToUnderline()
}


/**
 * 获取标题资源
 * 根据 clazz 的类名，生成字符串作为资源名，用资源名查找标题
 * 如果没有找到标题，直接返回类名（移除末尾的Activity）
 */
fun Context.getTitleFromRes(clazz: Class<out Any>): String {
    val titleResId = getIdentifier(clazz.getUnderlineNameWithoutActivity(), prefix = "title")
    return if (titleResId == 0) {
        //如果没声明标题，直接取名字
        clazz.simpleName.removeSuffix("Activity")
    } else {
        try {
            getString(titleResId)
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