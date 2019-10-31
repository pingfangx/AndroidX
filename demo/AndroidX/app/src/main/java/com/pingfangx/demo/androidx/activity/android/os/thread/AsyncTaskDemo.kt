package com.pingfangx.demo.androidx.activity.android.os.thread

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.extension.printCurrentThreadInfo
import com.pingfangx.demo.androidx.base.xxlog

/**
 *
 * @author pingfangx
 * @date 2019/10/31
 */
class AsyncTaskDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        activity.addButton("执行", View.OnClickListener {
            activity.printCurrentThreadInfo()
            val task = DemoAsyncTask()
            activity.printCurrentThreadInfo()
            task.execute(1, 2)
        })
    }
}

class DemoAsyncTask : AsyncTask<Int, Int, String>() {
    override fun doInBackground(vararg params: Int?): String {
        "doInBackground $params".xxlog()
        printCurrentThreadInfo()
        return "结果" + params
    }

    override fun onPreExecute() {
        super.onPreExecute()
        "onPreExecute".xxlog()
        printCurrentThreadInfo()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        "onPostExecute $result".xxlog()
        printCurrentThreadInfo()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        "onProgressUpdate $values".xxlog()
        printCurrentThreadInfo()
    }
}
