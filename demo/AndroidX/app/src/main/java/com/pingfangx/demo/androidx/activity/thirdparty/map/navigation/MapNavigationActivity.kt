package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app.*
import com.pingfangx.demo.androidx.base.BaseTipsActivity
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * 地图导航
 *
 * @author pingfangx
 * @date 2018/7/18
 */
class MapNavigationActivity : BaseTipsActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_base_list
    }

    override fun initViews() {
        super.initViews()
        recycler_view.layoutManager = LinearLayoutManager(this)

        val mapAppList = mutableListOf<BaseMapApp>()
        mapAppList.add(GaodeMapApp())
        mapAppList.add(BaiduMapApp())
        mapAppList.add(TencentMapApp())
        mapAppList.add(GoogleMapApp())
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler_view.adapter = object : BaseTextAdapter<BaseMapApp>(this, mapAppList) {
            override fun getItemText(t: BaseMapApp): String {
                return t.appName
            }
        }.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<BaseMapApp> {
            override fun onItemClick(view: View, position: Int, t: BaseMapApp) {
                t.navigate(view.context, NavigateLocation("39.90", "116.39", "天安门"))
            }
        })
    }
}