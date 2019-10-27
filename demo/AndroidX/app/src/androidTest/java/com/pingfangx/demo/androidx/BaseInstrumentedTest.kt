package com.pingfangx.demo.androidx

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * @author pingfant
 * @date 2019/10/27
 */
@RunWith(AndroidJUnit4::class)
open class BaseInstrumentedTest {
    companion object {
        const val TAG = "AndroidTest"
    }

    protected fun logDebug(obj: Any) {
        Log.d(TAG, obj.toString())
    }

    @Test
    open fun test() {
    }
}