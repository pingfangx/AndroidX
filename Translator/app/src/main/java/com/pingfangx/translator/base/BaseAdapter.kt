package com.pingfangx.translator.base

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * adapter
 *
 * @author pingfangx
 * @date 2017/9/10
 */
abstract class BaseAdapter<Bean, VH : BaseViewHolder<Bean>>(context: Context, data: List<Bean>) : RecyclerView.Adapter<VH>() {
    val mContext = context
    private val mData = data
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindTo(mData[position])
    }

    abstract fun getItemLayoutResId(): Int

    override fun getItemCount(): Int = mData.size
}