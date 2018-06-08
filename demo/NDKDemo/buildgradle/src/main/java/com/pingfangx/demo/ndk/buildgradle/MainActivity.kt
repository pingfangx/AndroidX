package com.pingfangx.demo.ndk.buildgradle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        System.loadLibrary("buildgradle")
        tv.text = reply("测试")
    }

    private external fun reply(text: String): String
}
