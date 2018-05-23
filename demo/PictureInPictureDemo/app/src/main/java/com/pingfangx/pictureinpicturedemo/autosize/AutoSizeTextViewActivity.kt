package com.pingfangx.pictureinpicturedemo.autosize

import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.pingfangx.pictureinpicturedemo.R
import kotlinx.android.synthetic.main.activity_auto_size_text_view.*

class AutoSizeTextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_size_text_view)
    }

    public fun onClickBtnAdd(view: View) {
        setText(true)
    }

    public fun onClickBtnSub(view: View) {
        setText(false)
    }


    public fun onClickBtnSetProgrammatically(view: View) {
        tv.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE)
        tv.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 10, 100, 2, TypedValue.COMPLEX_UNIT_SP)
        //要注意这里的 array 是 getIntArray，与 xml 中使用的带单位的不一样
        TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(tv, resources.getIntArray(R.array.auto_size_text_sizes_int), TypedValue.COMPLEX_UNIT_SP)
    }

    fun setText(add: Boolean) {
        val count = ll_container.childCount
        for (i in 0 until count) {
            var child = ll_container.getChildAt(i)
            if (child is TextView) {
                if (add) {
                    child.text = child.text.toString() + "字"
                } else {
                    child.text = child.text.dropLast(1)
                }
            }
        }
    }
}
