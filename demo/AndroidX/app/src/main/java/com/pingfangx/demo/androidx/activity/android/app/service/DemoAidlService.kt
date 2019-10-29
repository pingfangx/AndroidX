package com.pingfangx.demo.androidx.activity.android.app.service

import android.content.Intent
import android.os.IBinder

/**
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class DemoAidlService : LifecycleTestService() {
    private val mBinder: IBinder by lazy { DemoAidlBinder() }
    override fun onBind(intent: Intent?): IBinder? = mBinder
}

class DemoAidlBinder : IDemoAidlInterface.Stub() {
    override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
    }

    override fun calculate(a: Int, b: Int): Int = a + b
}