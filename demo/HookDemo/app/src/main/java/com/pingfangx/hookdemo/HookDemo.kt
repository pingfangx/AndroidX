package com.pingfangx.hookdemo

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 *
 * @author pingfangx
 * @date 2018/5/29
 */
class HookDemo : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        XposedBridge.log("xx Loaded app: " + lpparam?.packageName);
        val hook = object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("xx beforeHookedMethod")
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(methodHookParam: MethodHookParam) {
                methodHookParam.result = "new imei"
                XposedBridge.log("xx Hook device id is successful!!! ")
            }
        }
        findAndHookMethod("android.telephony.TelephonyManager", lpparam?.classLoader, "getDeviceId", hook)
    }
}