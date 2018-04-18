package com.pingfangx.butterknifedemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pingfangx.testmodule.TestActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv)
    lateinit var tv: TextView
    var mClickTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.tv)
    internal fun sayHello() {
        toast("点击")
        tv.text = "click ${++mClickTimes}"
    }

    @OnClick(R.id.start)
    internal fun start() {
        startActivity(Intent(this, TestActivity::class.java))
    }
}
