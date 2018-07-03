package com.pingfangx.demo.androidx.base

import android.content.Context
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewHolder
import kotlinx.android.synthetic.main.activity_base_activity_list.*

/**
 * Activity 列表
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseActivityListActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_base_activity_list
    }

    override fun initViews() {
        super.initViews()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val activityList = generateActivityList()
        recycler_view.adapter = ActivityAdapter(this, activityList)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    abstract fun generateActivityList(): MutableList<ActivityBean>
}

data class ActivityBean(val name: String, val clazz: Class<out Any>) {
    constructor(context: Context, clazz: Class<out Any>) : this(context.getTitleFromRes(clazz), clazz)
}

class ActivityAdapter(mContext: Context, mData: List<ActivityBean>) : BaseRecyclerViewAdapter<ActivityBean>(mContext, mData) {
    override fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<ActivityBean> {
        return ActivityViewHolder(itemView)
    }

    override fun getLayoutResId(): Int {
        return R.layout.item_activity
    }

    override fun onItemClick(t: ActivityBean) {
        super.onItemClick(t)
        val intent = Intent(mContext, t.clazz)
        mContext.startActivity(intent)
    }
}

class ActivityViewHolder(itemView: View) : BaseRecyclerViewHolder<ActivityBean>(itemView) {
    private val mTvName: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_title)
    }

    override fun bindTo(t: ActivityBean, position: Int) {
        mTvName.text = t.name
    }
}