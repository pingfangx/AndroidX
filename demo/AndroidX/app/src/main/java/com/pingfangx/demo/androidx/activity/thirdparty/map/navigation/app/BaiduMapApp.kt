package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.NavigateLocation

/**
 * 百度
 * 规划 http://lbsyun.baidu.com/index.php?title=uri/api/android#service-page-anchor9
 * 导航 http://lbsyun.baidu.com/index.php?title=uri/api/android#service-page-anchor10
 */
class BaiduMapApp : BaseMapApp() {

    override val appName = "百度地图"

    override val packageName = "com.baidu.BaiduMap"

    override fun getStartActivityUri(context: Context, location: NavigateLocation): String {
        return if (useRoutePlan) {
            "baidumap://map/direction?destination=latlng:${location.latitude},${location.longitude}|name:${location.name}&mode=driving&coord_type=gcj02"
        } else {
            "baidumap://map/navi?location=${location.latitude},${location.longitude}&coord_type=gcj02"
        }
    }
}
