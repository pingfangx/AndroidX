package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.NavigateLocation
import org.jetbrains.anko.toast

/**
 * 地图软件
 */
abstract class BaseMapApp {
    /**
     * 软件名
     */
    abstract val appName: String
    /**
     * 包名
     */
    abstract val packageName: String

    /**
     * 是否启用路径规划
     * 如果为否，会直接进入导航
     */
    open var useRoutePlan: Boolean = true

    fun navigate(context: Context, location: NavigateLocation) {
        if (!MapAppUtil.checkInstalled(context, packageName)) {
            //未安装
            onAppNotInstall(context, location)
        } else {
            onAppInstalled(context, location)
        }
    }

    /**
     * 未安装
     */
    protected open fun onAppNotInstall(context: Context, location: NavigateLocation) {
        context.toast("您还没有安装$appName")
    }

    /**
     * 已安装
     */
    protected fun onAppInstalled(context: Context, location: NavigateLocation) {
        val uri = getStartActivityUri(context, location)
        startActivity(context, uri)
    }

    /**
     * 获取启动 Activity 的 uri
     */
    protected open fun getStartActivityUri(context: Context, location: NavigateLocation): String {
        return ""
    }

    /**
     * 启动 Activity
     */
    fun startActivity(context: Context, uri: String) {
        val intent = Intent()
        intent.setPackage(packageName)
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            context.toast("打开导航失败$appName")
        }

    }
}
