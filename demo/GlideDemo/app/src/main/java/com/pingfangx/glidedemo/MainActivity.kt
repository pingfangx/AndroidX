package com.pingfangx.glidedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this)
                .load(R.drawable.bg_my_info2)
                .into(iv1)
        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
                .load(R.drawable.bg_my_info2)
                .into(iv2)
    }
}
