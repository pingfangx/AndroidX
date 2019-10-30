package com.pingfangx.demo.androidx.activity.android.content.receiver

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.activity.android.app.job.DemoJobService
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.extension.hasTrueExtra
import com.pingfangx.demo.androidx.base.extension.printCurrentThreadInfo
import com.pingfangx.demo.androidx.base.extension.simpleClassName
import com.pingfangx.demo.androidx.base.xxlog
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

/**
 * 广播接收器
 *
 * @author pingfangx
 * @date 2019/10/30
 */
abstract class LifecycleReceiver : BroadcastReceiver() {
    companion object {
        const val EXTRA_ABORT = "EXTRA_ABORT"
        const val EXTRA_TIME_OUT = "EXTRA_TIME_OUT"
        const val EXTRA_GO_ASYNC = "EXTRA_GO_ASYNC"
        const val EXTRA_JOB_SCHEDULER = "EXTRA_JOB_SCHEDULER"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val text = "${simpleClassName()} onReceive"
        text.xxlog()
        context?.toast(text)
        if (intent?.getBooleanExtra(EXTRA_ABORT, false) == true) {
            abortBroadcast()
        }
        context?.printCurrentThreadInfo()
        checkAndTimeOut(intent)
        checkAndGoAsnyc(intent)
        if (context != null) {
            checkAndJobScheduler(context, intent)
        }
    }

    private fun checkAndTimeOut(intent: Intent?) {
        if (!intent.hasTrueExtra(EXTRA_TIME_OUT)) {
            return
        }
        for (i in 1..11) {
            i.toString().xxlog()
            TimeUnit.SECONDS.sleep(1)
        }
    }

    private fun checkAndGoAsnyc(intent: Intent?) {
        if (!intent.hasTrueExtra(EXTRA_GO_ASYNC)) {
            return
        }
        val pendingResult: PendingResult? = goAsync()
        if (pendingResult != null) {
            val asyncTask = InnerTask(pendingResult, intent)
            asyncTask.execute()
        } else {
            "为空".xxlog()
        }
    }

    private class InnerTask(
            private val pendingResult: PendingResult,
            private val intent: Intent?) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String {
            for (i in 1..11) {
                i.toString().xxlog()
                TimeUnit.SECONDS.sleep(1)
            }
            return intent?.action ?: ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pendingResult.finish()
        }
    }

    private fun checkAndJobScheduler(context: Context, intent: Intent?) {
        if (!intent.hasTrueExtra(EXTRA_JOB_SCHEDULER)) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val jobInfo: JobInfo = JobInfo.Builder(1, ComponentName(context, DemoJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setRequiresCharging(true)
                    .build()
            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.schedule(jobInfo)
            "执行schedule".xxlog()
        }
    }
}

class ManifestDeclaredReceiver : LifecycleReceiver()
class ManifestDeclaredWithPermissionReceiver : LifecycleReceiver()
class ContextRegisteredReceiver : LifecycleReceiver()
class ContextRegisteredReceiver2 : LifecycleReceiver()
class LocalContextRegisteredReceiver : LifecycleReceiver()

class ReceiverDemo : ActivityLifecycle {
    companion object {
        const val ACTION_CONTEXT_REGISTER = "com.pingfangx.demo.android.ACTION_CONTEXT_REGISTER"
    }

    private val contextRegisteredReceiver by lazy { ContextRegisteredReceiver() }
    private val contextRegisteredReceiver2 by lazy { ContextRegisteredReceiver2() }
    private val localContextRegisteredReceiver by lazy { LocalContextRegisteredReceiver() }

    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)

        // context 注册广播
        val intentFilter = IntentFilter(ACTION_CONTEXT_REGISTER)
        activity.registerReceiver(contextRegisteredReceiver, intentFilter)
        activity.registerReceiver(contextRegisteredReceiver2, intentFilter)
        LocalBroadcastManager.getInstance(activity).registerReceiver(localContextRegisteredReceiver, intentFilter)

        //manifest-declared
        activity.addButton("sendBroadcast 隐式发送给 Manifest 声明的接收器(不允许)", View.OnClickListener {
            //Background execution not allowed
            activity.sendBroadcast(Intent(ManifestDeclaredReceiver::class.java.name))
        })

        //不可以隐式注册广播，如果指定 package 或 component
        val manifestIntent = Intent(ManifestDeclaredReceiver::class.java.name)
                .setPackage(activity.packageName)
        //或者
//        activity.sendBroadcast(Intent(ManifestDeclaredReceiver::class.java.name)
//                .setClassName(activity.packageName, ManifestDeclaredReceiver::class.java.name))
        activity.addButton("sendBroadcast setPackage 发送给 Manifest 声明的接收器", View.OnClickListener {
            activity.sendBroadcast(manifestIntent)
        })

        // context-registered
        val contextIntent = Intent(ACTION_CONTEXT_REGISTER)
        activity.addButton("sendBroadcast 给 Context 注册的接收器 (多个)", View.OnClickListener { activity.sendBroadcast(contextIntent) })
        activity.addButton("sendOrderedBroadcast 不中断", View.OnClickListener { activity.sendOrderedBroadcast(contextIntent, activity.getString(R.string.com_pingfangx_demo_androidx_permission)) })
        activity.addButton("sendOrderedBroadcast 中断", View.OnClickListener {
            activity.sendOrderedBroadcast(contextIntent.putExtra(LifecycleReceiver.EXTRA_ABORT, true), activity.getString(R.string.com_pingfangx_demo_androidx_permission))
        })


        activity.addButton("带权限发送（需要 uses-permission 的接收者才能接收）", View.OnClickListener {
            //直接发送可以接收，但是如果带权限，则需要接收者声明权限才可以接收
            activity.sendBroadcast(manifestIntent, activity.getString(R.string.com_pingfangx_demo_androidx_permission))
        })

        activity.addButton("带权限接收（需要 uses-permission 的发送者才能发送）", View.OnClickListener {
            activity.sendBroadcast(Intent(ManifestDeclaredWithPermissionReceiver::class.java.name)
                    .setPackage(activity.packageName))
        })

        //互不干扰，彼此注册的只是各自接收，不会互串
        activity.addButton("LocalBroadcastManager.sendBroadcast", View.OnClickListener { LocalBroadcastManager.getInstance(activity).sendBroadcast(contextIntent) })

        activity.addButton("发送并超时", View.OnClickListener {
            activity.printCurrentThreadInfo()
            LocalBroadcastManager.getInstance(activity).sendBroadcast(contextIntent.putExtra(LifecycleReceiver.EXTRA_TIME_OUT, true))
        })
        activity.addButton("发送并 goAsync", View.OnClickListener {
            LocalBroadcastManager.getInstance(activity).sendBroadcast(contextIntent.putExtra(LifecycleReceiver.EXTRA_GO_ASYNC, true))
        })
        activity.addButton("发送并 jobScheduler", View.OnClickListener {
            LocalBroadcastManager.getInstance(activity).sendBroadcast(contextIntent.putExtra(LifecycleReceiver.EXTRA_JOB_SCHEDULER, true))
        })
    }

    override fun onDestroy(activity: BaseActivity) {
        super.onDestroy(activity)
        activity.unregisterReceiver(contextRegisteredReceiver)
        activity.unregisterReceiver(contextRegisteredReceiver2)
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(localContextRegisteredReceiver)
    }
}