package com.pingfangx.pictureinpicturedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class ViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)
        initViews()
    }

    private fun initViews() {
        var tv = findViewById<TextView>(R.id.tv)
        tv = findViewById(R.id.tv)
    }
}
