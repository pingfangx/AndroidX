package com.pingfangx.demo.proguarddemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
    }

    fun onClickTvTest(view: View) {
        ProGuardTest().keep()
    }
}
