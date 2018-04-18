package com.pingfangx.testmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import org.jetbrains.anko.toast

class TestActivity : AppCompatActivity() {

    @BindView(R2.id.tv)
    lateinit var tv: TextView
    var mClickTimes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        ButterKnife.bind(this)
    }
    @OnClick(R2.id.tv)
    internal fun sayHello() {
        toast("点击")
        tv.text = "click ${++mClickTimes}"
    }
}
