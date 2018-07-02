package com.pingfangx.demo.androidx.base

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.pingfangx.demo.androidx.activity.view.TextViewBoldActivity
import org.junit.Assert
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
        val titleResId = appContext.getTitleResId(TextViewBoldActivity::class.java)
        if (titleResId != 0) {
            val title = appContext.getString(titleResId)
            title.xxlog()
        }
        Assert.assertNotEquals(titleResId, 0)
    }
}