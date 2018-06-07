package com.pingfangx.demo.douyindemo

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ss.android.common.applog.UserInfo
import java.util.*

/**
 *
 * 参照数据
arg 0
1528625757
arg 1
http://aweme.snssdk.com/aweme/v1/feed/?type=0&max_cursor=0&min_cursor=0&count=6&retry_type=retry_http&iid=34856908524&device_id=53510367487&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=159&version_name=1.5.9&device_platform=android&ssmix=a&device_type=SM-G955N&device_brand=Android&os_api=22&os_version=5.1.1&uuid=354730010156788&openudid=9c4e365961d88690&manifest_version_code=159&resolution=1080*1920&dpi=320&update_version_code=1592&ts=1528625757&app_type=normal
arg 2
[iid, 34856908524, device_id, 53510367487, ac, wifi, channel, wandoujia, aid, 1128, app_name, aweme, version_code, 159, version_name, 1.5.9, device_platform, android, ssmix, a, device_type, SM-G955N, device_brand, Android, os_api, 22, os_version, 5.1.1, uuid, 354730010156788, openudid, 9c4e365961d88690, manifest_version_code, 159, resolution, 1080*1920, dpi, 320, update_version_code, 1592, app_type, normal]
result is
a1b5af115d854b6afcfe56be5cdbcc17aee1



arg 0
1528632771
arg 1
https://api.amemv.com/aweme/v1/feed/?type=0&max_cursor=0&min_cursor=0&count=6&retry_type=no_retry&iid=34856908524&device_id=53510367487&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=159&version_name=1.5.9&device_platform=android&ssmix=a&device_type=SM-G955N&device_brand=Android&os_api=22&os_version=5.1.1&uuid=354730010156788&openudid=9c4e365961d88690&manifest_version_code=159&resolution=1080*1920&dpi=320&update_version_code=1592&ts=1528632771&app_type=normal
arg 2
[iid, 34856908524, device_id, 53510367487, ac, wifi, channel, wandoujia, aid, 1128, app_name, aweme, version_code, 159, version_name, 1.5.9, device_platform, android, ssmix, a, device_type, SM-G955N, device_brand, Android, os_api, 22, os_version, 5.1.1, uuid, 354730010156788, openudid, 9c4e365961d88690, manifest_version_code, 159, resolution, 1080*1920, dpi, 320, update_version_code, 1592, app_type, normal]
result is
a105c1e1539cebd51d16cdb05f3ed2165ce1

请求
type	0
max_cursor	0
min_cursor	0
count	6
retry_type	retry_http
iid	34856908524
device_id	53510367487
ac	wifi
channel	wandoujia
aid	1128
app_name	aweme
version_code	159
version_name	1.5.9
device_platform	android
ssmix	a
device_type	SM-G955N
device_brand	Android
os_api	22
os_version	5.1.1
uuid	354730010156788
openudid	9c4e365961d88690
manifest_version_code	159
resolution	1080*1920
dpi	320
update_version_code	1592
ts	1528625757
app_type	normal
as	a1b5af115d854b6afc
cp	fe56be5cdbcc17aee1
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
    }

    fun onClickBtnLoadSo(view: View) {
        System.loadLibrary("userinfo")
    }

    fun onClickBtnGetUserInfo(view: View) {
        getTest()
        get()
    }

    private fun getTest() {
//        val time = 1528625757

        val time = (System.currentTimeMillis() / 1000).toInt()
        val url = "http://aweme.snssdk.com/aweme/v1/feed/?type=0&max_cursor=0&min_cursor=0&count=6&retry_type=retry_http&iid=34856908524&device_id=53510367487&ac=wifi&channel=wandoujia&aid=1128&app_name=aweme&version_code=159&version_name=1.5.9&device_platform=android&ssmix=a&device_type=SM-G955N&device_brand=Android&os_api=22&os_version=5.1.1&uuid=354730010156788&openudid=9c4e365961d88690&manifest_version_code=159&resolution=1080*1920&dpi=320&update_version_code=1592&ts=$time&app_type=normal"
        val argArray = arrayOf("iid", "34856908524", "device_id", "53510367487", "ac", "wifi", "channel", "wandoujia", "aid", "1128", "app_name", "aweme", "version_code", "159", "version_name", "1.5.9", "device_platform", "android", "ssmix", "a", "device_type", "SM-G955N", "device_brand", "Android", "os_api", "22", "os_version", "5.1.1", "uuid", "354730010156788", "openudid", "9c4e365961d88690", "manifest_version_code", "159", "resolution", "1080*1920", "dpi", "320", "update_version_code", "1592", "app_type", "normal")
        val result = UserInfo.getUserInfo(time, url, argArray)
        log("result is ")
        log(result)
        val length = result.length
        log(url + "&as=${result.substring(0, length / 2)}&cp=${result.substring(length / 2, length)}")
    }

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

    fun get() {
        val time = (System.currentTimeMillis() / 1000).toInt()

        var url = "https://api.amemv.com/aweme/v1/feed/?"
        val paramStringBuilder = StringBuilder()
        val paramsMap = getCommonParams()
        for (entry in paramsMap) {
            paramStringBuilder.append(entry.key)
            paramStringBuilder.append('=')
            paramStringBuilder.append(entry.value)
            paramStringBuilder.append('&')
        }
        if (paramStringBuilder.endsWith('&')) {
            paramStringBuilder.deleteCharAt(paramStringBuilder.length - 1)
        }
        url += paramStringBuilder.toString()
        val asAndCp = UserInfo.getUserInfo(time, url, getArgArray())
        log("arg0 $time")
        log("arg1 $url")
        log("arg2")
        log(getArgArray())
        log("result $asAndCp")
    }

    /**
     * 写死就好了
     */
    fun getArgArray(): Array<String> {
        return arrayOf("iid", "34828217664", "device_id", "49308486101", "ac", "wifi", "channel", "wandoujia", "aid", "1128", "app_name", "aweme", "version_code", "159", "version_name", "1.5.9", "device_platform", "android", "ssmix", "a", "device_type", "ASUS_Z00AD", "device_brand", "asus", "os_api", "19", "os_version", "4.4.2", "uuid", "352649010401410", "openudid", "408d5c4e3ba34963", "manifest_version_code", "159", "resolution", "720*1280", "dpi", "240", "update_version_code", "1592", "app_type", "normal")
    }

    fun getCommonParams(): MutableMap<String, String> {
        val paramsMap = mutableMapOf<String, String>()
        val paramString = "type\t0\n" +
                "max_cursor\t0\n" +
                "min_cursor\t0\n" +
                "count\t6\n" +
                "retry_type\tno_retry\n" +
                "iid\t34828217664\n" +
                "device_id\t49308486101\n" +
                "ac\twifi\n" +
                "channel\twandoujia\n" +
                "aid\t1128\n" +
                "app_name\taweme\n" +
                "version_code\t159\n" +
                "version_name\t1.5.9\n" +
                "device_platform\tandroid\n" +
                "ssmix\ta\n" +
                "device_type\tASUS_Z00AD\n" +
                "device_brand\tasus\n" +
                "os_api\t19\n" +
                "os_version\t4.4.2\n" +
                "uuid\t352649010401410\n" +
                "openudid\t408d5c4e3ba34963\n" +
                "manifest_version_code\t159\n" +
                "resolution\t720*1280\n" +
                "dpi\t240\n" +
                "update_version_code\t1592\n" +
                "ts\t1528370705\n" +
                "app_type\tnormal\n" +
                "as\ta1d54131d1710b2639\n" +
                "cp\t1811bf571e951e67e1";
        val lines = paramString.split("\n")
        for (line in lines) {
            val keyValue = line.split("\t")
            paramsMap.put(keyValue[0], keyValue[1])
        }
        //需要计算
        paramsMap.remove("as")
        paramsMap.remove("cp")
        return paramsMap
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