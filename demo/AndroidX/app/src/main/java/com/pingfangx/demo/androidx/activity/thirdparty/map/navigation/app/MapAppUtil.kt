package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 * 地图app的工具
 *
 * @author pingfangx
 * @date 2017/3/9.
 */
object MapAppUtil {
    /**
     * 检查是否已安装
     *
     * @param context context
     * @param packageName 包名
     * @return 是否已安装
     */
    fun checkInstalled(context: Context, packageName: String): Boolean {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            return packageInfo != null
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun checkInstalled2(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager
        val packageInfos = packageManager.getInstalledPackages(0)
        for (packageInfo in packageInfos) {
            if (packageName == packageInfo.packageName) {
                return true
            }
        }
        return false
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    fun openApplicationMarket(context: Context, packageName: String) {
        try {
            val str = "market://details?id=$packageName"
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.data = Uri.parse(str)
            context.startActivity(localIntent)
        } catch (e: Exception) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace()
            Toast.makeText(context, "打开应用商店失败", Toast.LENGTH_SHORT).show()
        }

    }
}
