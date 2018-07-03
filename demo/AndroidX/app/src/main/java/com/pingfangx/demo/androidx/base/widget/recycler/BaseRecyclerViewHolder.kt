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
    var onItemClickListener: OnItemClickListener? = null

    init {
        itemView.setOnClickListener { view: View -> onItemClickListener?.onItemClick(view, adapterPosition) }
    }

    abstract fun bindTo(t: T, position: Int)
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
