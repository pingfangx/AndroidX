package com.pingfangx.bitmapdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.pingfangx.bitmapdemo.shader.ShaderActivity
import com.pingfangx.bitmapdemo.xfermode.XfermodeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickBtnCornerDemo(view: View) {
        startActivity(Intent(this, CornerDemoActivity::class.java))
    }

    fun onClickBtnShaderDemo(view: View) {
        startActivity(Intent(this, ShaderActivity::class.java))
    }

    fun onClickBtnXfermode(view: View) {
        startActivity(Intent(this, XfermodeActivity::class.java))
    }

    fun onClickBtnRotateBitmap(view: View) {
        startActivity(Intent(this, RotateBitmapActivity::class.java))
    }
}
