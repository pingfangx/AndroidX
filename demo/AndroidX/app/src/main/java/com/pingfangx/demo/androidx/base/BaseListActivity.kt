package com.pingfangx.demo.androidx.base

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
    protected val mAdapter by lazy {
        createAdapter()
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
        mRecyclerView.adapter = mAdapter
    }


    abstract fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<*>

    protected open fun createLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

    protected open fun createItemDecoration(): androidx.recyclerview.widget.RecyclerView.ItemDecoration? = androidx.recyclerview.widget.DividerItemDecoration(this, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL)
}