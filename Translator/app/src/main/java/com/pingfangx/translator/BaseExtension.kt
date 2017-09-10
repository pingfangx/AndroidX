package com.pingfangx.translator

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * 基本的扩展
 *
 * @author pingfangx
 * @date 2017/9/10
 */

/**
 * toast
 */
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * 输出
 */
fun Any.log() {
    Log.d("xx", this.toString())
}