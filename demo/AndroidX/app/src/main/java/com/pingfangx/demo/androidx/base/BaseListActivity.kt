package com.pingfangx.demo.androidx.base

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pingfangx.demo.androidx.R
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * 列表
 *
 * @author pingfangx
 * @date 2018/9/19
 */
abstract class BaseListActivity : BaseActivity() {
    protected val mRecyclerView by lazy {
        recycler_view
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_base_list
    }

    override fun initViews() {
        super.initViews()
        val itemDecoration = createItemDecoration()
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration)
        }
        mRecyclerView.layoutManager = createLayoutManager()
        mRecyclerView.adapter = createAdapter()
    }


    abstract fun createAdapter(): RecyclerView.Adapter<*>

    protected open fun createLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    protected open fun createItemDecoration(): RecyclerView.ItemDecoration? = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
}