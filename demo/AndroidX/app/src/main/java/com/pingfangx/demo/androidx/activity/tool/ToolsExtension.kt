package com.pingfangx.demo.androidx.activity.tool

import android.os.Build

/**
 * 工具的扩展
 *
 * @author pingfangx
 * @date 2018/10/8
 */

/**
 * 获取支持的 abi
 */
fun getSupportedAbi(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //adb shell getprop ro.product.cpu.abilist
        Build.SUPPORTED_ABIS.joinToString(",")
    } else {
        //adb shell getprop ro.product.cpu.abi
        @Suppress("DEPRECATION")//已经判断版本号
        Build.CPU_ABI + "," + Build.CPU_ABI2
    }
}