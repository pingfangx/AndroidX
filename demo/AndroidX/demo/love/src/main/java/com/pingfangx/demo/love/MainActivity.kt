package com.pingfangx.demo.love

import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.toast

/**
 * 学习手势的
 */
class MainActivity : AppCompatActivity() {
    companion object {
        const val MODE_SAVE = 1
        const val MODE_RECOGNIZE = 2
        const val TAG = "MainActivity"
        const val KEY_DRAW_TIMES = "draw_times"
        const val KEY_DRAW_LOVE_TIMES = "draw_love_times"
    }

    private var mode: Int = 2
    private val name = "爱心"
    private val sp by lazy {
        defaultSharedPreferences
    }
    private var draw_times = 0
    private var draw_love_times = 0
    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.textView)
    }
    private val gestureLibrary: GestureLibrary by lazy {
        val library = GestureLibraries.fromRawResource(this, R.raw.gestures)
        library.load()
        library
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        draw_times = sp.getInt(KEY_DRAW_TIMES, 0)
        draw_love_times = sp.getInt(KEY_DRAW_LOVE_TIMES, 0)
        val gestureOverlayView = findViewById<GestureOverlayView>(R.id.gestureOverlayView)
        gestureOverlayView.addOnGesturePerformedListener { overlay, gesture ->
            when (mode) {
                MODE_SAVE -> {
                    overlay.context.toast("添加手势 [$name]")
                    gestureLibrary.addGesture(name, gesture)
                    gestureLibrary.save()
                }
                MODE_RECOGNIZE -> {
                    val recognize = gestureLibrary.recognize(gesture)
                    Log.d(TAG, recognize.joinToString(separator = "\n") { "${it.name}:${it.score}" })
                    val max = recognize.maxBy { it.score }
                    if (max != null && max.score > 2) {
                        val text = when (max.name) {
                            "爱心" -> when {
                                max.score > 10 -> {
                                    //爱心
                                    draw_love_times++
                                    when {
                                        draw_love_times > 9 -> {
                                            val n = Math.floor(Math.log10(draw_love_times.toDouble())).toInt()
                                            getString(R.string.gesture_love_you_forever, draw_love_times, "永远".repeat(n))
                                        }
                                        draw_love_times > 1 -> getString(R.string.gesture_love_you_times, draw_love_times)
                                        else -> getText(R.string.gesture_love_you)
                                    }
                                }
                                //不 > 10
                                else -> getText(R.string.gesture_not_quite_like)
                            }
                            //不是爱心
                            else -> getString(R.string.gesture_guess_your_drawn, max.name)
                        }
                        textView.text = getString(R.string.gesture_show_info, text, max.score)
                        textView.context.toast(text)
                    } else {
                        val text = getText(R.string.gesture_what_is_your_drawn)
                        textView.text = text
                        textView.context.toast(text)
                    }
                    sp.edit()
                            .putInt(KEY_DRAW_TIMES, ++draw_times)
                            .putInt(KEY_DRAW_LOVE_TIMES, draw_love_times)
                            .apply()
                    Log.d(TAG, "draw times $draw_times")
                }
                else -> overlay.context.toast("手势绘制完成")
            }
        }
    }
}
