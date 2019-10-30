package com.pingfangx.demo.androidx.activity.android.app.anr

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pingfangx.demo.androidx.activity.android.app.service.LifecycleTestService
import com.pingfangx.demo.androidx.activity.android.content.receiver.LifecycleReceiver
import com.pingfangx.demo.androidx.activity.android.content.receiver.ManifestDeclaredReceiver
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.extension.threadSleep

/**
 *
 * @author pingfangx
 * @date 2019/10/30
 */
class AnrDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        activity.addButton("点击 ANR", View.OnClickListener {
            threadSleep(5 + 10)
        })
        activity.addButton("Service ANR", View.OnClickListener {
            Intent(activity, LifecycleTestService::class.java)
                    .putExtra(LifecycleTestService.EXTRA_TIME_OUT, true)
                    .also { activity.startService(it) }
        })
        activity.addButton("Broadcast ANR", View.OnClickListener {
            Intent(ManifestDeclaredReceiver::class.java.name)
                    .setPackage(activity.packageName)
                    .putExtra(LifecycleReceiver.EXTRA_TIME_OUT, true)
                    .also {
                        activity.sendBroadcast(it)
                    }
        })
    }
}