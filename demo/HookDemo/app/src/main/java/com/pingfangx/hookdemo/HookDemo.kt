package com.pingfangx.hookdemo

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.util.*

/**
 *
 * @author pingfangx
 * @date 2018/5/29
 */
class HookDemo : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam == null) {
            return
        }
        log("loaded app: " + lpparam.packageName);

        if (lpparam.packageName.startsWith("com.pingfangx")) {
            val hook = object : MethodHook("getDeviceId") {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    super.afterHookedMethod(param)
                    param?.result = "new imei"
                }
            }
            findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", hook)
        }

        if (lpparam.packageName.equals("com.ss.android.ugc.aweme")) {
            log("进入抖音")
            try {
                XposedHelpers.findAndHookMethod("com.ss.android.common.applog.UserInfo",
                        lpparam.classLoader,
                        "getUserInfo", Int::class.javaPrimitiveType, String::class.java, Array<String>::class.java,
                        MethodHook("getUserInfo-3"))

                XposedHelpers.findAndHookMethod("com.ss.android.common.applog.UserInfo",
                        lpparam.classLoader,
                        "getUserInfo", Int::class.javaPrimitiveType, String::class.java, Array<String>::class.java, String::class.java,
                        MethodHook("getUserInfo-4"))

                XposedHelpers.findAndHookMethod("com.ss.android.common.applog.UserInfo",
                        lpparam.classLoader,
                        "initUser", String::class.java,
                        MethodHook("initUser"))
            } catch (e: Exception) {
                log("出错" + Log.getStackTraceString(e))
            }
        }
    }

    open class MethodHook(val name: String) : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            super.beforeHookedMethod(param)
            log("${name} beforeHookedMethod")
        }

        override fun afterHookedMethod(param: MethodHookParam?) {
            super.afterHookedMethod(param)
            log("${name} afterHookedMethod")
            param?.let {
                val args = param.args
                args?.let {
                    for (i in 0 until args.size) {
                        log("arg $i \n")
                        log(args[i])
                    }
                }
                log("result is \n${param.result}")
            }
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