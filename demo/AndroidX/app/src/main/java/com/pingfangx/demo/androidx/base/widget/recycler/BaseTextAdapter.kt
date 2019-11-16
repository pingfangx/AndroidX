package com.pingfangx.demo.androidx.base.widget.recycler

import android.content.Context
import android.view.View
import android.widget.TextView
import com.pingfangx.demo.androidx.R

/**
 * 文字的 Adapter
 *
 * @author pingfangx
 * @date 2018/7/18
 */
open class BaseTextAdapter<T>(mContext: Context, mData: List<T>) : BaseRecyclerViewAdapter<T>(mContext, mData) {
    override fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<T> {
        return object : BaseTextViewHolder<T>(itemView) {
            override fun getText(t: T): String {
                return getItemText(t)
            }
        }
    }

    /**
     * 获取 item 展示的文字
     */
    protected open fun getItemText(t: T): String = t.toString()

    override fun getLayoutResId(): Int {
        return R.layout.item_base_text
    }
}

/**
 * 文字的 ViewHolder
 */
open class BaseTextViewHolder<T>(itemView: View) : BaseRecyclerViewHolder<T>(itemView) {
    val mTvName: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_title)
    }

    override fun bindTo(t: T, position: Int) {
        mTvName.text = getText(t)
    }

    /**
     * 获取展示的文字
     */
    protected open fun getText(t: T): CharSequence = t.toString()
}