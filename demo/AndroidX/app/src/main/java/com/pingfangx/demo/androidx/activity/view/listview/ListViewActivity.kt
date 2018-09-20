package com.pingfangx.demo.androidx.activity.view.listview

import android.content.Context
import android.widget.ArrayAdapter
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_view.*

/**
 * 用于调试 ListView 理解源码
 *
 * @author pingfangx
 * @date 2018/9/20
 */
class ListViewActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        val listView = list_view
        val data = mutableListOf<Int>()
        for (i in 0..100) {
            data.add(i)
        }
        listView.adapter = Adapter(this, R.layout.item_base_text, R.id.tv_title, data)
    }

    private class Adapter(context: Context?, resource: Int, textViewResourceId: Int, objects: MutableList<Int>?) : ArrayAdapter<Int>(context, resource, textViewResourceId, objects) {
        override fun getItemViewType(position: Int): Int {
            return position % 2
        }

        override fun getViewTypeCount(): Int {
            return 2
        }
    }
}