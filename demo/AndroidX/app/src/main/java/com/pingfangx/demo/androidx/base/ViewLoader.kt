package com.pingfangx.demo.androidx.base

/**
 * 加载 view
 *
 * @author pingfangx
 * @date 2018/7/2
 */
interface ViewLoader {
    /**
     * 布局资源 id
     */
    fun getItemLayoutResId(): Int

    /**
     * 初始化
     */
    fun initViews() {}
}