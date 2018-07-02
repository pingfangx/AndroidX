package com.pingfangx.demo.androidx.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 基类
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseActivity : AppCompatActivity(), ViewLoader {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getItemLayoutResId())
        initViews()
    }

    override fun initViews() {
        super.initViews()
        val title = this.getTitleFromRes(this::class.java)
        if (title.isNotEmpty()) {
            setTitle(title)
        }
    }
}