package com.pingfangx.demo.androidx.activity.android.app.job

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import androidx.annotation.RequiresApi
import com.pingfangx.demo.androidx.base.xxlog

/**
 *
 *
 * @author pingfangx
 * @date 2019/10/30
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DemoJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        "onStartJob".xxlog()
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        "onStopJob".xxlog()
        return false
    }
}