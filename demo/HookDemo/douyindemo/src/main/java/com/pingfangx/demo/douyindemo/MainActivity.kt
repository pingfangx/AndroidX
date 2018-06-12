package com.pingfangx.demo.douyindemo

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ss.android.common.applog.GlobalContext
import com.ss.android.common.applog.UserInfo
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
    }

    /**
     * 加载 so
     * setContext 与 setAppId 是必须的
     */
    fun onClickBtnLoadSo(view: View) {
        GlobalContext.setContext(this)
        System.loadLibrary("userinfo")
        UserInfo.setAppId(2)
    }

    /**
     * 初始化用户，sp 中没有保存时，使用的默认值
     */
    fun onClickBtnInitUser(view: View) {
        val result = UserInfo.initUser("a3668f0afac72ca3f6c1697d29e0e1bb1fef4ab0285319b95ac39fa42c38d05f")
        log("initUser result $result")
    }

    /**
     * 测试
     */
    fun onClickBtnGetUserInfo(view: View) {
        getTest()
        get()
    }

    /**
     * 与抓取的测试数据比较
     */
    private fun getTest() {
        val time = 1528804357
        val url = "http://aweme.snssdk.com/aweme/v1/feed/?type=0&max_cursor=0&min_cursor=0&count=6&retry_type=retry_http&iid=35306463530&device_id=53675840580&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=159&version_name=1.5.9&device_platform=android&ssmix=a&device_type=SM-G955N&device_brand=Android&os_api=22&os_version=5.1.1&uuid=354730010401416&openudid=408d5c4e3ba32650&manifest_version_code=159&resolution=1080*1920&dpi=320&update_version_code=1592&ts=$time&app_type=normal"
        val argArray = arrayOf("iid", "35306463530", "device_id", "53675840580", "ac", "wifi", "channel", "wandoujia", "aid", "1128", "app_name", "aweme", "version_code", "159", "version_name", "1.5.9", "device_platform", "android", "ssmix", "a", "device_type", "SM-G955N", "device_brand", "Android", "os_api", "22", "os_version", "5.1.1", "uuid", "354730010401416", "openudid", "408d5c4e3ba32650", "manifest_version_code", "159", "resolution", "1080*1920", "dpi", "320", "update_version_code", "1592", "app_type", "normal")
        val result = UserInfo.getUserInfo(time, url, argArray)
        log("result is ")
        log(result)
        val testResult = "a1c57bd1c570eb845fbd09bc565bfd134ee1"
        log("result same : ${result.equals(testResult)}")
        val length = result.length
        log(url + "&as=${result.substring(0, length / 2)}&cp=${result.substring(length / 2, length)}")
    }

    /**
     * 实时更新的时间
     */
    fun get() {
        val time = (System.currentTimeMillis() / 1000).toInt()
        val url = "http://aweme.snssdk.com/aweme/v1/feed/?type=0&max_cursor=0&min_cursor=0&count=6&retry_type=retry_http&iid=35306463530&device_id=53675840580&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=159&version_name=1.5.9&device_platform=android&ssmix=a&device_type=SM-G955N&device_brand=Android&os_api=22&os_version=5.1.1&uuid=354730010401416&openudid=408d5c4e3ba32650&manifest_version_code=159&resolution=1080*1920&dpi=320&update_version_code=1592&ts=$time&app_type=normal"
        val argArray = arrayOf("iid", "35306463530", "device_id", "53675840580", "ac", "wifi", "channel", "wandoujia", "aid", "1128", "app_name", "aweme", "version_code", "159", "version_name", "1.5.9", "device_platform", "android", "ssmix", "a", "device_type", "SM-G955N", "device_brand", "Android", "os_api", "22", "os_version", "5.1.1", "uuid", "354730010401416", "openudid", "408d5c4e3ba32650", "manifest_version_code", "159", "resolution", "1080*1920", "dpi", "320", "update_version_code", "1592", "app_type", "normal")
        val result = UserInfo.getUserInfo(time, url, argArray)
        log("result is ")
        log(result)
        val length = result.length
        log(url + "&as=${result.substring(0, length / 2)}&cp=${result.substring(length / 2, length)}")
    }

    /**
     * 签名修改后的测试，修改见 AwemeApplication
     * 后来发现与签名无关
     */
    fun onClickBtnGetSignature(view: View) {
        log("抖音签名")
        var packageInfo: PackageInfo?
        try {
            packageInfo = packageManager.getPackageInfo("com.ss.android.ugc.aweme", PackageManager.GET_SIGNATURES)
            if (packageInfo != null) {
                log(packageInfo.signatures[0].toCharsString())
            }
        } catch (e: Exception) {
        }


        log("当前签名")
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            log(packageInfo.signatures[0].toCharsString())
        } catch (e: Exception) {
        }

    }

}

fun log(o: Any) {
    if (o::class.java.isArray) {
        log(Arrays.toString(o as Array<*>))
    } else {
        log(o.toString())
    }
}

fun log(string: String) {
    Log.d("xxflag", string)
}