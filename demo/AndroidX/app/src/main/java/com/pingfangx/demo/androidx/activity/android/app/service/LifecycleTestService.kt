package com.pingfangx.demo.androidx.activity.android.app.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.core.app.NotificationCompat
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.activity.android.app.notification.NotificationDemo
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.*
import com.pingfangx.demo.androidx.base.xxlog
import com.pingfangx.demo.androidx.common.VirtualActivity
import org.jetbrains.anko.toast

/**
 * 服务
 *
 * @author pingfangx
 * @date 2019/10/29
 */
open class LifecycleTestService : Service() {
    companion object {
        const val EXTRA_AUTO_STOP = "EXTRA_AUTO_STOP"
        const val EXTRA_FOREGROUND = "EXTRA_FOREGROUND"
        const val EXTRA_TIME_OUT = "EXTRA_TIME_OUT"

        const val CHANNEL_ID = "前台服务"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        "${simpleClassName()} onCreate".xxlog()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "${simpleClassName()} onStartCommand".xxlog()
        "intent 为 $intent".xxlog()
        printCurrentThreadInfo()
        toast("onStartCommand")

        checkAndStartForeground(intent)
        checkAndAutoStop(intent)
        checkAndTimeOut(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        "${simpleClassName()} onBind".xxlog()
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        "${simpleClassName()} onUnbind".xxlog()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        "${simpleClassName()} onRebind".xxlog()
        super.onRebind(intent)
    }

    override fun onDestroy() {
        "${simpleClassName()} onDestroy".xxlog()
        stopForeground(true)
        super.onDestroy()
    }

    private fun checkAndStartForeground(intent: Intent?) {
        if (intent?.getBooleanExtra(EXTRA_FOREGROUND, false) == true) {
            "启动前台服务".xxlog()
            val activityIntent = VirtualActivity.createInent(this, ServiceLifecycleDemo::class.java.name)
            val pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)
            val channel = NotificationDemo.createChannel(this, CHANNEL_ID, channelDesc = "用于前台服务")
            val notification = NotificationCompat.Builder(this, channel)
                    .setContentTitle("标题")
                    .setContentText("内容")
                    .setTicker("ticker")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build()
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun checkAndAutoStop(intent: Intent?) {
        if (intent?.getBooleanExtra(EXTRA_AUTO_STOP, false) == true) {
            Thread(Runnable {
                "3s 后自动停止".xxlog()
                Thread.sleep(3000)
                stopSelf()
            }).start()
        }
    }

    private fun checkAndTimeOut(intent: Intent?) {
        if (intent.hasTrueExtra(EXTRA_TIME_OUT)) {
            threadSleep(60 + 10)
        }
    }
}

/**
 * 在 manifest 中指定进程
 */
class AnotherProcessService : LifecycleTestService()

class ServiceLifecycleDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)
        activity.addButton("startService", View.OnClickListener {
            activity.printCurrentThreadInfo()
            activity.startService(Intent(activity, LifecycleTestService::class.java))
        })
        activity.addButton("stopService", View.OnClickListener {
            activity.stopService(Intent(activity, LifecycleTestService::class.java))
        })
        activity.addButton("startServiceAndAutoStop", View.OnClickListener {
            activity.startService(
                    Intent(activity, LifecycleTestService::class.java)
                            .putExtra(LifecycleTestService.EXTRA_AUTO_STOP, true)
            )
        })
        activity.addButton("startForegroundService", View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //注意 28 以后需要声明权限 FOREGROUND_SERVICE（不需要请求）
                activity.startForegroundService(
                        Intent(activity, LifecycleTestService::class.java)
                                .putExtra(LifecycleTestService.EXTRA_FOREGROUND, true)
                )
            }
        })

        activity.addButton("起动另一进程的服务", View.OnClickListener {
            activity.printCurrentThreadInfo()
            activity.startService(Intent(activity, AnotherProcessService::class.java))
        })
        activity.addButton("隐式 intent 启动", View.OnClickListener {
            try {
                activity.startService(Intent(""))
            } catch (e: Exception) {
                e.printStackTrace()
                activity.toast("启动失败" + e.message)
            }
        })
        activity.addButton("启动并超时", View.OnClickListener { })
    }
}