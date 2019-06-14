package com.pingfangx.demo.androidx.base

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