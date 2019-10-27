package com.pingfangx.demo.androidx.study.source.android.util

import android.util.SparseArray
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pingfangx.demo.androidx.BaseInstrumentedTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * SparseArray
 *
 * @author pingfangx
 * @date 2019/10/27
 */
@RunWith(AndroidJUnit4::class)
class SparseArrayTest : BaseInstrumentedTest() {
    @Test
    fun test_put() {
        val sparseArray = SparseArray<Int>(2)
        for (i in 1 until 10) {
            sparseArray.put(i * 10, i)
            logDebug("结果是" + sparseArray.get(i * 10))
        }
    }
}