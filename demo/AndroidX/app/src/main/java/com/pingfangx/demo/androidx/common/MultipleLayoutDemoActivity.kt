package com.pingfangx.demo.androidx.common

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pingfangx.demo.androidx.base.BaseListActivity
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_LAYOUT
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_TITLE
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter

/**
 * 多个布局示例，用于纯布局展示，通过 addLayoutInfo 添加布局
 *
 * @author pingfangx
 * @date 2019/5/9
 */
abstract class MultipleLayoutDemoActivity : BaseListActivity() {

    private val mLayoutList: MutableList<LayoutInfo> = mutableListOf()

    protected fun addLayoutInfo(layoutResId: Int, desc: String = "") {
        addLayoutInfo(LayoutInfo(layoutResId, desc))
    }

    protected fun addLayoutInfo(layoutInfo: LayoutInfo) {
        mLayoutList.add(layoutInfo)
    }

    override fun getLayoutResId(): Int {
        val layoutResIdFromIntent = intent.getIntExtra(INTENT_EXTRA_LAYOUT, 0)
        return if (layoutResIdFromIntent != 0) {
            mIsDetail = true
            layoutResIdFromIntent
        } else {
            super.getLayoutResId()
        }
    }

    override fun createAdapter(): RecyclerView.Adapter<*> {
        return object : BaseTextAdapter<LayoutInfo>(this, mLayoutList) {
            override fun getItemText(t: LayoutInfo): String {
                return if (t.desc.isNotEmpty()) {
                    t.desc
                } else {
                    resources.getResourceEntryName(t.layoutResId)
                }
            }
        }.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<LayoutInfo> {
            override fun onItemClick(view: View, position: Int, t: LayoutInfo) {
                startActivity(t)
            }
        })
    }

    private fun startActivity(layoutInfo: LayoutInfo) {
        val targetActivity = if (layoutInfo.targetActivity != null) {
            layoutInfo.targetActivity
        } else {
            getActivityClass()
        }
        startActivity(Intent(this, targetActivity)
                .putExtra(INTENT_EXTRA_LAYOUT, layoutInfo.layoutResId)
                .putExtra(INTENT_EXTRA_TITLE, layoutInfo.desc))
    }

    open protected fun getActivityClass(): Class<out Activity> {
        //复用直接返回 this
        return this::class.java
    }

    inner class LayoutInfo(var layoutResId: Int, var desc: String = "", var targetActivity: Class<out Activity>? = null)
}