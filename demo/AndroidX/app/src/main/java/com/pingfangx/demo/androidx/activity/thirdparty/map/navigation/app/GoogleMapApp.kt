package com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.app

import android.content.Context
import com.pingfangx.demo.androidx.activity.thirdparty.map.navigation.NavigateLocation

/**
 * 谷歌地图
 * https://developers.google.com/maps/documentation/urls/guide#directions-action
 *
 * @author pingfangx
 * @date 2018/7/13
 */
class GoogleMapApp : BaseMapApp() {
    override val appName = "谷歌地图"
    override val packageName = "com.google.android.apps.maps"

    override fun getStartActivityUri(context: Context, location: NavigateLocation): String {
        return "https://www.google.com/maps/dir/?api=1&destination=${location.latitude},${location.longitude}&travelmode=driving${if (useRoutePlan) "" else "&dir_action=navigate"}"
    }
}
