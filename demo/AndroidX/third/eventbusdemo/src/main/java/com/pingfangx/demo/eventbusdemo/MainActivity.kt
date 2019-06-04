package com.pingfangx.demo.eventbusdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d(TAG, "onMessageEvent ${event.text}")
        Toast.makeText(this, "onMessageEvent ${event.text}", Toast.LENGTH_SHORT).show()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onStickyMessageEvent(event: StickMessageEvent) {
        Log.d(TAG, "onStickyMessageEvent ${event.text}")
        Toast.makeText(this, "onStickyMessageEvent ${event.text}", Toast.LENGTH_SHORT).show()
    }

    fun onClickPostEvent(view: View) {
        EventBus.getDefault().post(MessageEvent("main"))
    }


    fun onClickPostEventInThread(view: View) {
        object : Thread() {
            override fun run() {
                super.run()
                EventBus.getDefault().post(MessageEvent("from thread"))
            }
        }.start()
    }

    fun onClickPostStickEvent(view: View) {
        //发送后，当前页面就会收到，并且再启动 Activity 后，也会收到
        //如果不停启动，也会不停收到
        EventBus.getDefault().postSticky(StickMessageEvent("sticky"))
    }

    fun onClickRemoveStickEvent(view: View) {
        EventBus.getDefault().removeStickyEvent(StickMessageEvent::class.java)
        Toast.makeText(this, "remove sticky event", Toast.LENGTH_SHORT).show()
    }

    fun onClickStartActivity(view: View) {
        startActivity(Intent(this, this::class.java))
    }
}

open class StickMessageEvent(val text: String = "") {}
class MessageEvent(val text: String) {}
