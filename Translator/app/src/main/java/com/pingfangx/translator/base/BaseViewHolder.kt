package com.pingfangx.translator.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 基类的view holder
 *
 * @author pingfangx
 * @date 2017/9/10
 */
abstract class BaseViewHolder<in Bean>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mOnItemClickListener: OnItemClickListener? = null

    init {
        itemView.setOnClickListener { mOnItemClickListener?.onItemClick(adapterPosition) }
    }

    abstract fun bindTo(bean: Bean)
    interface OnItemClickListener {
        /**
         * 点击item
         */
        fun onItemClick(position: Int)
    }
}