package com.pingfangx.demo.androidx.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val titleFromRes = appContext.getTitleFromRes(TextViewBoldActivity::class.java)
        titleFromRes.xxlog()
        assert(titleFromRes.isNotEmpty())
    }
}