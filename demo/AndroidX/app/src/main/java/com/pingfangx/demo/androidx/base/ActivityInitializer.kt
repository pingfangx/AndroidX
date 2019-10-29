package com.pingfangx.demo.androidx.base

import android.content.Intent
import android.os.Bundle

/**
 * Activity 初始化，使用类来进行初始化，这样就不用都声明 Activity
 *
 * @author pingfangx
 * @date 2019/6/12
 */
interface ActivityInitializer {
    /**
     * 初始化
     */
    fun initActivity(activity: BaseActivity) {}

    /**
     * 可以指定布局 id
     */
    fun getLayoutResId(): Int = 0
}

interface ActivityLifecycle : ActivityInitializer {
    fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {}
    fun onStart(activity: BaseActivity) {}
    fun onPause(activity: BaseActivity) {}
    fun onResume(activity: BaseActivity) {}
    fun onStop(activity: BaseActivity) {}
    fun onDestroy(activity: BaseActivity) {}
    fun onRestart(activity: BaseActivity) {}
    fun onNewIntent(activity: BaseActivity, intent: Intent?) {}
    fun onRequestPermissionsResult(activity: BaseActivity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}
    fun onActivityResult(activity: BaseActivity, requestCode: Int, resultCode: Int, data: Intent?) {}
}