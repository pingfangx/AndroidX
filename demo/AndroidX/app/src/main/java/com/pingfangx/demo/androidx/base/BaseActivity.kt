package com.pingfangx.demo.androidx.base

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_LAYOUT
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_TITLE
import kotlinx.android.synthetic.main.activity_base_list.*
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
        val layoutResIdFromIntent = intent.getIntExtra(INTENT_EXTRA_LAYOUT, 0)
        return if (layoutResIdFromIntent != 0) {
            layoutResIdFromIntent
        } else {
            getLayoutResIdFromResources()
        }
    }

    override fun initViews() {
        super.initViews()
        val titleFromIntent: String? = intent.getStringExtra(INTENT_EXTRA_TITLE)
        val title = if (titleFromIntent?.isNotEmpty() == true) {
            titleFromIntent
        } else {
            getTitleResIdFromResources()
        }
        if (title.isNotEmpty()) {
            setTitle(title)
        }
        initTips()
    }

    /**
     * 设置提示
     */
    private fun initTips() {
        val tips = getTips()
        if (tips.isNotEmpty()) {
            //需要设置
            var tvTips = tv_tips
            if (tvTips == null) {
                contentView ?: return
                tvTips = TextView(this)
                (contentView as ViewGroup).addView(tvTips)
            }
            tvTips.text = tips
        }
    }

    /**
     * 获取 tips
     */
    protected open fun getTips(): CharSequence {
        val tipsResId = getTipsResIdFromResources()
        if (tipsResId != 0) {
            return getText(tipsResId)
        }
        return ""
    }

    protected open fun getLayoutResIdFromResources(): Int = getDefaultLayoutResId()
    protected open fun getTitleResIdFromResources(): String = getTitleFromRes(this::class.java)
    protected open fun getTipsResIdFromResources(): Int = getIdentifier(prefix = "tips")

}