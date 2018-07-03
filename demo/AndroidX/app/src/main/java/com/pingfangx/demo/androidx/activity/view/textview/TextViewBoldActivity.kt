package com.pingfangx.demo.androidx.activity.view.textview

import android.graphics.Typeface
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_text_view_bold.*

/**
 * 文字加粗
 *
 * @author pingfangx
 * @date 2018/7/2
 */
class TextViewBoldActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        tv_set_typeface.setTypeface(null, Typeface.BOLD)
        tv_set_fake_bold_text.paint.isFakeBoldText = true
    }
}