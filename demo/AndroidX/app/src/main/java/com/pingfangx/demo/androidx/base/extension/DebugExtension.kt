package com.pingfangx.demo.androidx.base.extension

import android.content.Context
import com.pingfangx.demo.androidx.base.xxlog

/**
 * 用于调试
 *
 * @author pingfangx
 * @date 2019/10/30
 */

fun Context.printCurrentThreadInfo() {
    "当前进程 ${this.getProcessName()},当前线程 tid=${Thread.currentThread().id}, ${Thread.currentThread()}".xxlog()
}