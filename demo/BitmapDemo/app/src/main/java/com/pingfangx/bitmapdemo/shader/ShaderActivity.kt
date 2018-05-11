package com.pingfangx.bitmapdemo.shader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.pingfangx.bitmapdemo.R
import com.pingfangx.bitmapdemo.shader.tile.TileModeActivity

class ShaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shader)
    }

    fun onClickBtnTileMode(view: View) {
        startActivity(Intent(this, TileModeActivity::class.java))
    }
}

