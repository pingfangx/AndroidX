package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.NavigateLocation

/**
 * 高德
 * 规划 https://lbs.amap.com/api/amap-mobile/guide/android/route
 * 导航 https://lbs.amap.com/api/amap-mobile/guide/android/navigation
 */
class GaodeMapApp : BaseMapApp() {
    override val appName = "高德地图"
    override val packageName = "com.autonavi.minimap"

    override fun getStartActivityUri(context: Context, location: NavigateLocation): String {
        val appName = context.getString(R.string.app_name)
        //注意纬度在前
        return if (useRoutePlan) {
            "amapuri://route/plan/?sourceApplication=$appName&dlat=${location.latitude}&dlon=${location.longitude}&dname=${location.name}&dev=0&t=0"
        } else {
            "androidamap://navi?sourceApplication=$appName&lat=${location.latitude}&lon=${location.longitude}&dev=0&style=2"
        }
    }

}
