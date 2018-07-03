package com.pingfangx.demo.androidx.base

import com.pingfangx.demo.androidx.R

/**
 * 仅显示 tips
 *
 * @author pingfangx
 * @date 2018/7/18
 */
abstract class BaseTipsActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        //供子类使用
        return R.layout.activity_base_tips
    }
}