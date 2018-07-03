package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.NavigateLocation

/**
 * 腾讯地图
 * http://lbs.qq.com/uri_v1/guide-mobile-navAndRoute.html
 *
 * @author pingfangx
 * @date 2018/7/13
 */
class TencentMapApp : BaseMapApp() {
    override val appName = "腾讯地图"
    override val packageName = "com.tencent.map"

    override fun getStartActivityUri(context: Context, location: NavigateLocation): String {
        return "qqmap://map/routeplan?type=drive&fromcoord=CurrentLocation&tocoord=${location.latitude},${location.longitude}&to=${location.name}"
    }
}
