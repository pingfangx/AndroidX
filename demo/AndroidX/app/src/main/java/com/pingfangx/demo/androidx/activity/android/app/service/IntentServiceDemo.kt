package com.pingfangx.demo.androidx.activity.android.app.service

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.extension.printCurrentThreadInfo

/**
 *
 * @author pingfangx
 * @date 2019/10/31
 */
class DemoIntentService : IntentService("IntentServiceDemo") {
    override fun onHandleIntent(intent: Intent?) {
        printCurrentThreadInfo()
    }
}

class IntentServiceDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)

        activity.addButton("startService", View.OnClickListener {
            activity.printCurrentThreadInfo()
            activity.startService(Intent(activity, DemoIntentService::class.java))
        })
        activity.addButton("stopService", View.OnClickListener {
            activity.stopService(Intent(activity, DemoIntentService::class.java))
        })
    }
}