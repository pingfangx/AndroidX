package com.pingfangx.demo.androidx.base.widget.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pingfangx.demo.androidx.base.ViewLoader

/**
 * 基类 adapter
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseRecyclerViewAdapter<T>(val mContext: Context, private val mData: List<T>) : RecyclerView.Adapter<BaseRecyclerViewHolder<T>>(), ViewLoader, OnItemClickListener {

    private val mOnItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<T> {
        val itemView = onCreateItemView(parent, viewType)
        itemView ?: let {
            throw RuntimeException("The item view should not be null.")
        }
        val viewHolder = onCreateViewHolder(itemView)
        if (mOnItemClickListener != null) {
            viewHolder.setOnItemClickListener(mOnItemClickListener)
        } else {
            //作默认处理
            viewHolder.setOnItemClickListener(this)
        }
        return viewHolder
    }

    protected open fun onCreateItemView(parent: ViewGroup, viewType: Int): View? {
        val layoutResId = getLayoutResId()
        return if (layoutResId > 0) {
            LayoutInflater.from(mContext).inflate(layoutResId, parent, false)
        } else {
            null
        }
    }

    override fun getLayoutResId(): Int {
        return 0
    }

    abstract fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<T>


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T>, position: Int) {
        holder.bindTo(mData[position], position)
    }

    //点击 item
    override fun onItemClick(view: View, position: Int) {
        if (position > -1 && position < mData.size) {
            val t = mData[position]
            t?.let {
                onItemClick(t)
            }
        }
    }

    /**
     * 点击了 item
     */
    protected open fun onItemClick(t: T) {

    }
}