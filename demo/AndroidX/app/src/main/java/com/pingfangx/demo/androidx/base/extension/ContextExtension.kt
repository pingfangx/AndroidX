package com.pingfangx.demo.androidx.base.extension

import android.app.ActivityManager
import android.content.Context

/**
 *
 *
 * @author pingfangx
 * @date 2019/10/29
 */

/**
 * android.app.Application.getProcessName
 */
fun Context.getProcessName(): String = getProcessName(android.os.Process.myPid())

fun Context.getProcessName(pid: Int): String {
    val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (process in am.runningAppProcesses) {
        if (pid == process.pid) {
            return process.processName
        }
    }
    return ""
}