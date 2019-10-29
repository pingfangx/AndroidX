package com.pingfangx.demo.androidx.activity.android.app.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.xxlog
import org.jetbrains.anko.toast
import kotlin.random.Random

/**
 * bindService 使用 Messenger
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class MessengerService : LifecycleTestService() {
    private lateinit var mMessenger: Messenger

    /**
     * 注意官方指南中在这里的处理，不持有 contenxt （没有声明 val 或 var）
     * 而是取为 applicationContext
     */
    inner class IncomingHandler(context: Context, private val applicationContext: Context = context.applicationContext) : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val text = "服务接收到消息" + msg.what
            applicationContext.toast(text)
            text.xxlog()
            printCurrentThreadInfo(applicationContext)

            msg.replyTo?.send(Message.obtain(null, msg.what shl 1))
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        toast("绑定中")
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }
}

class MessengerServiceDemo : ActivityLifecycle {
    private var mBound: Boolean = false
    private var mServerMessenger: Messenger? = null
    private var mClientMessenger: Messenger? = null

    inner class ClientHandler(context: Context, private val applicationContext: Context = context.applicationContext) : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val text = "客户端收到回复" + msg.what
            applicationContext.toast(text)
            text.xxlog()
            printCurrentThreadInfo(applicationContext)
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            "onServiceConnected".xxlog()
            mServerMessenger = Messenger(service)
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServerMessenger = null
            mBound = false
        }
    }

    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        Thread.currentThread().name = "客户端主线程"
        mClientMessenger = Messenger(ClientHandler(activity))

        activity.addButton("绑定", View.OnClickListener {
            activity.bindService(Intent(activity, MessengerService::class.java), mConnection, Context.BIND_AUTO_CREATE)
        })
        activity.addButton("发送消息", View.OnClickListener {
            if (mBound) {
                val message = Message.obtain(null, Random.nextInt(100))
                message.replyTo = mClientMessenger
                mServerMessenger?.send(message)
            }
        })
    }

    override fun onDestroy(activity: BaseActivity) {
        super.onDestroy(activity)
        if (mBound) {
            activity.unbindService(mConnection)
            mBound = false
        }
    }

}