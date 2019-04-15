package com.pingfangx.demo.androidx.base

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.pingfangx.demo.androidx.activity.view.textview.TextViewBoldActivity
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 * @author pingfangx
 * @date 2018/7/2
 */
@RunWith(AndroidJUnit4::class)
class ExtensionInstrumentedTest {
    @Test
    fun getTitleResIdTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val titleFromRes = appContext.getTitleFromRes(TextViewBoldActivity::class.java)
        titleFromRes.xxlog()
        assert(titleFromRes.isNotEmpty())
    }
}