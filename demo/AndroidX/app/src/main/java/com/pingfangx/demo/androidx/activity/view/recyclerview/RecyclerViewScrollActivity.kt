package com.pingfangx.demo.androidx.activity.view.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.activity.view.recyclerview.widget.LinearLayoutManager
import com.pingfangx.demo.androidx.activity.view.recyclerview.widget.RecyclerView
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.ViewLoader

/**
 * RecyclerView 滚动
 *
 * @author pingfangx
 * @date 2019/2/19
 */
class RecyclerViewScrollActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view);
        val data: MutableList<String> = mutableListOf()
        for (i in 0..100) {
            data.add(i.toString())
        }
        recyclerView.adapter = BaseTextAdapter(this, data)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    class BaseTextAdapter<T>(mContext: Context, mData: List<T>) : BaseRecyclerViewAdapter<T>(mContext, mData) {
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
        private val mTvName: TextView by lazy {
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

    abstract class BaseRecyclerViewAdapter<T>(val mContext: Context, private val mData: List<T>) : com.pingfangx.demo.androidx.activity.view.recyclerview.widget.RecyclerView.Adapter<BaseRecyclerViewHolder<T>>(), ViewLoader {
        var onItemClickListener: OnItemClickListener<T>? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<T> {
            val itemView = onCreateItemView(parent, viewType)
            itemView ?: let {
                throw RuntimeException("The item view should not be null.")
            }
            val viewHolder = onCreateViewHolder(itemView)
            viewHolder.onItemClickListener = object : BaseRecyclerViewHolder.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    if (position > -1 && position < mData.size) {
                        val t = mData[position]
                        t?.let {
                            onItemClick(view, position, t)
                        }
                    }
                }
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

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>): BaseRecyclerViewAdapter<T> {
            this.onItemClickListener = onItemClickListener
            return this
        }

        /**
         * 点击了 item
         */
        protected open fun onItemClick(view: View, position: Int, t: T) {
            onItemClickListener?.onItemClick(view, position, t)
        }

        interface OnItemClickListener<T> {
            fun onItemClick(view: View, position: Int, t: T)
        }
    }

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
}

