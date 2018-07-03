package com.pingfangx.demo.androidx.activity.view.textview

import android.graphics.Typeface
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_text_view_skew.*

/**
 * 查看字体加粗的时候看到，简单了解一下。
 *
 * @author pingfangx
 * @date 2018/7/3
 */
class TextViewSkewActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        tv_use_typeface.setTypeface(null, Typeface.ITALIC)
        tv_use_paint.paint.textSkewX = -0.25F
        tv_1.paint.textSkewX = -0.5F
        tv_2.paint.textSkewX = -1F
        tv_3.paint.textSkewX = 0.25F
    }
}