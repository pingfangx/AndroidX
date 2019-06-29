package com.pingfangx.demo.love

import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.toast
import java.io.File

/**
 * 学习手势的
 */
class MainActivity : AppCompatActivity() {
    companion object {
        const val MODE_DRAW = 1
        const val MODE_RECOGNIZE = 2
        const val TAG = "MainActivity"
        const val KEY_DRAW_TIMES = "draw_times"
        const val KEY_DRAW_LOVE_TIMES = "draw_love_times"
    }

    private var mode: Int = 2
    private val loveGestureName by lazy {
        getString(R.string.gesture_heart_gesture_name)
    }
    private val sp by lazy {
        defaultSharedPreferences
    }
    private var drawTimes = 0
    private var drawLoveTimes = 0
    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.textView)
    }
    private val fileGestureLibrary: GestureLibrary by lazy {
        GestureLibraries.fromFile(File(filesDir, "gestures"))
    }
    private lateinit var gestureLibrary: GestureLibrary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initGestureLibrary()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 1, 0, getString(R.string.gesture_draw_your_heart))
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu?.add(0, 2, 0, getString(R.string.gesture_toggle_mode, getString(R.string.gesture_mode_recognize)))
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            1 -> {
                toggleDrawMode()
                return true
            }
            2 -> {
                toggleRecognizeMode()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initGestureLibrary() {
        if (fileGestureLibrary.load()) {
            gestureLibrary = fileGestureLibrary
        } else {
            //默认是文件的，如果文件未加载，则加载默认的资源
            gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures)
            gestureLibrary.load()
        }
    }

    private fun initViews() {
        drawTimes = sp.getInt(KEY_DRAW_TIMES, 0)
        drawLoveTimes = sp.getInt(KEY_DRAW_LOVE_TIMES, 0)
        val gestureOverlayView = findViewById<GestureOverlayView>(R.id.gestureOverlayView)
        gestureOverlayView.addOnGesturePerformedListener { overlay, gesture ->
            when (mode) {
                MODE_DRAW -> {
                    showSaveDialog(gesture)
                }
                MODE_RECOGNIZE -> {
                    val recognize = gestureLibrary.recognize(gesture)
                    Log.d(TAG, recognize.joinToString(separator = "\n") { "${it.name}:${it.score}" })
                    val max = recognize.maxBy { it.score }
                    if (max != null && max.score > 2) {
                        val text = when (max.name) {
                            loveGestureName -> when {
                                max.score > 10 -> {
                                    //爱心
                                    drawLoveTimes++
                                    when {
                                        drawLoveTimes > 9 -> {
                                            val n = Math.floor(Math.log10(drawLoveTimes.toDouble())).toInt()
                                            getString(R.string.gesture_love_you_forever, drawLoveTimes, "永远".repeat(n))
                                        }
                                        drawLoveTimes > 1 -> getString(R.string.gesture_love_you_times, drawLoveTimes)
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
                            .putInt(KEY_DRAW_TIMES, ++drawTimes)
                            .putInt(KEY_DRAW_LOVE_TIMES, drawLoveTimes)
                            .apply()
                    Log.d(TAG, "draw times $drawTimes")
                }
                else -> overlay.context.toast(R.string.gesture_toast_draw_finish)
            }
        }
    }

    private fun showSaveDialog(gesture: Gesture) {
        val dialog = AlertDialog.Builder(this)
                .setMessage(R.string.gesture_dialog_save_gesture_message)
                .setPositiveButton(R.string.gesture_dialog_save_gesture_save) { _, _ ->
                    saveGesture(gesture)
                    toggleRecognizeMode()
                }
                .setNegativeButton(R.string.gesture_dialog_save_gesture_cancel, null)
                .create()
        dialog.show()
    }

    private fun saveGesture(gesture: Gesture) {
        toast(getString(R.string.gesture_toast_save_gesture_success, loveGestureName))
        fileGestureLibrary.addGesture(loveGestureName, gesture)
        fileGestureLibrary.save()
        //文件手势库才可以保存，赋值
        gestureLibrary = fileGestureLibrary
    }

    private fun toggleDrawMode() {
        mode = MODE_DRAW
        toast(getString(R.string.gesture_toggle_mode, getString(R.string.gesture_mode_draw)))
        textView.setText(R.string.gesture_draw_your_heart)
    }

    private fun toggleRecognizeMode() {
        mode = MODE_RECOGNIZE
        toast(getString(R.string.gesture_toggle_mode, getString(R.string.gesture_mode_recognize)))
        textView.setText(R.string.gesture_please_draw_a_heart)
    }
}
