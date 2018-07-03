package com.pingfangx.demo.androidx.base

import android.content.Context
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * Activity 列表
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseActivityListActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_base_list
    }

    override fun initViews() {
        super.initViews()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val activityList = generateActivityList()
        val textAdapter = object : BaseTextAdapter<ActivityBean>(this, activityList) {
            override fun getItemText(t: ActivityBean): String {
                return t.name
            }
        }.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ActivityBean> {
            override fun onItemClick(view: View, position: Int, t: ActivityBean) {
                val intent = Intent(view.context, t.clazz)
                view.context.startActivity(intent)
            }
        })
        recycler_view.adapter = textAdapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    abstract fun generateActivityList(): MutableList<ActivityBean>
}

data class ActivityBean(val name: String, val clazz: Class<out Any>) {
    constructor(context: Context, clazz: Class<out Any>) : this(context.getTitleFromRes(clazz), clazz)
}