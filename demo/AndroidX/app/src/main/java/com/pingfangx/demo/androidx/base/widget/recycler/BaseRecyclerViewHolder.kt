package com.pingfangx.demo.androidx.base.widget.recycler

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 基类 ViewHolder
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseRecyclerViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mOnItemClickListener: OnItemClickListener? = null

    init {
        itemView.setOnClickListener { view: View -> mOnItemClickListener?.onItemClick(view, adapterPosition) }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    abstract fun bindTo(t: T, position: Int)
}

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
}