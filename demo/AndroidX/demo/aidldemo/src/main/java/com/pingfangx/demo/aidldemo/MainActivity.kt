package com.pingfangx.demo.aidldemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pingfangx.demo.androidx.activity.android.app.service.IDemoAidlInterface
import org.jetbrains.anko.toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var mBound: Boolean = false
    private var mDemoAidlInterface: IDemoAidlInterface? = null

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mDemoAidlInterface = IDemoAidlInterface.Stub.asInterface(service)
            mBound = true
            toast("绑定成功，当前类为 ${mDemoAidlInterface?.javaClass?.name}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickBtnBind(view: View) {
        Intent()
                .setComponent(ComponentName("com.pingfangx.demo.androidx", "com.pingfangx.demo.androidx.activity.android.app.service.DemoAidlService"))
                .also { intent ->
                    try {
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        toast("绑定失败 $e")
                    }
                }
    }

    fun onClickBtnCal(view: View) {
        if (!mBound) {
            toast("还未绑定")
            return
        }
        val a = Random.nextInt(10)
        val b = Random.nextInt(10)
        try {
            val result = mDemoAidlInterface?.calculate(a, b)
            toast("计算 $a+$b=$result")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBound) {
            unbindService(mConnection)
        }
    }
}
