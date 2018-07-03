package com.pingfangx.demo.androidx.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_base_activity_list.*
import org.jetbrains.anko.contentView

/**
 * 基类
 * 布局命名为 activity_
 * 标题命名为 title_
 * 说明命名为 tips_
 *
 * 如果找到资源，会自动设置
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseActivity : AppCompatActivity(), ViewLoader {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initViews()
    }

    open fun setContentView() {
        val itemLayoutResId = getLayoutResId()
        if (itemLayoutResId > 0) {
            setContentView(itemLayoutResId)
        }
    }

    override fun getLayoutResId(): Int {
        return getDefaultLayoutResId()
    }

    override fun initViews() {
        super.initViews()
        val title = this.getTitleFromRes(this::class.java)
        if (title.isNotEmpty()) {
            setTitle(title)
        }
        initTips()
    }

    /**
     * 设置提示
     */
    private fun initTips() {
        val tipsResId = getIdentifier(prefix = "tips")
        if (tipsResId != 0) {
            //需要设置
            var tvTips = tv_tips
            if (tvTips == null) {
                contentView ?: return
                tvTips = TextView(this)
                (contentView as ViewGroup).addView(tvTips)
            }
            tvTips.setText(tipsResId)
        }

    }


}