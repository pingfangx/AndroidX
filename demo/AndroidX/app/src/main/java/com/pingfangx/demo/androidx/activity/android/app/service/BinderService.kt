package com.pingfangx.demo.androidx.activity.android.app.service

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.xxlog
import org.jetbrains.anko.toast

/**
 * 测试 bind
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class BinderService : LifecycleTestService() {

    override fun onBind(intent: Intent?): IBinder? = LocolBinder()

    fun getCurrentTime() = System.currentTimeMillis()

    inner class LocolBinder : Binder() {
        fun getService() = this@BinderService
    }
}

class BinderServiceDemo : ActivityLifecycle {
    private var mService: BinderService? = null
    private var mBound: Boolean = false
    private lateinit var mTvTips: TextView
    val connection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BinderService.LocolBinder
            mService = binder.getService()
            mBound = true
            "onServiceConnected".xxlog()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
            "onServiceDisconnected".xxlog()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        mTvTips = activity.findViewById(R.id.tv_tips)

        val serviceIntent = Intent(activity, BinderService::class.java)
        activity.addButton("start", View.OnClickListener {
            activity.startService(serviceIntent)
        })
        activity.addButton("bindTo", View.OnClickListener {
            val success = activity.bindService(serviceIntent, connection, 0)
            "绑定结果 $success".xxlog()
        })
        activity.addButton("bindTo BIND_AUTO_CREATE", View.OnClickListener {
            val success = activity.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
            "绑定结果 $success".xxlog()
        })
        activity.addButton("读取", View.OnClickListener {
            if (mBound) {
                val service = mService
                service?.let {
                    mTvTips.text = "从 service 中读取到时间 ${service.getCurrentTime()}"
                }
            } else {
                activity.toast("还未绑定")
            }
        })
    }

    override fun onDestroy(activity: BaseActivity) {
        super.onDestroy(activity)
        if (mBound) {
            activity.unbindService(connection)
            mBound = false
        }
    }
}