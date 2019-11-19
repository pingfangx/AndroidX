package com.pingfangx.demo.glidedemo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://www.baidu.com/img/bd_logo1.png"
        setLoad(findViewById(R.id.iv_image_1), url)
        setLoad(findViewById(R.id.iv_image_2), url)
    }

    private fun setLoad(iv: ImageView, url: String) {
        iv.setOnClickListener {
            Glide.with(this)
                    .load(url)
                    .into(iv)
        }
    }
}
