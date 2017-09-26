package com.pingfangx.demo.drawable

import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.ArcShape
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

/**
 * 相关博文：http://blog.pingfangx.com/2385.html
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById<TextView>(R.id.tv)
        val background: Drawable? = tv.background
        background?.let {
            Log.d("xx", background.javaClass.name)
            if (background is ShapeDrawable) {
                val arcShape: ArcShape = ArcShape(0f, 270f)
                background.shape = arcShape
            }
        }
    }
}
