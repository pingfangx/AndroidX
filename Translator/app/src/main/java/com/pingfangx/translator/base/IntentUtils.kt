package com.pingfangx.translator.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.io.Serializable

/**
 * 处理intent相关
 *
 * @author pingfangx
 * @date 2017/9/10
 */
class IntentUtils {
    companion object {
        /**
         * 这两个name本来是应该以包名开头的
         */
        val INTENT_EXTRA_COMMON = "INTENT_EXTRA_COMMON"
        val INTENT_EXTRA_FROM = "INTENT_EXTRA_FROM"

        /**
         * 启动Activity
         */
        fun startActivity(context: Context, targetActivity: Class<*>, extraValue: Any? = null) {
            startActivity(context, targetActivity, INTENT_EXTRA_COMMON, extraValue)
        }

        fun startActivity(context: Context, targetActivity: Class<*>, extraKey: String = INTENT_EXTRA_COMMON, extraValue: Any?) {
            val startIntent = createStartIntent(context, targetActivity, extraKey, extraValue)
            startActivity(context, startIntent)
        }

        /**
         * 最终打开的方法
         */
        private fun startActivity(context: Context, startIntent: Intent) {
            context.startActivity(startIntent)
        }

        fun createStartIntent(context: Context, targetActivity: Class<*>, extraKey: String, extraValue: Any?): Intent {
            val startIntent = Intent()
            startIntent.setClass(context, targetActivity)
            startIntent.putExtras(createBundle(extraKey, extraValue))
            return startIntent
        }

        fun getExtra(bundle: Bundle?, defaultObject: Any = ""): Any? = getExtra(bundle, INTENT_EXTRA_COMMON, defaultObject)

        fun getExtra(intent: Intent?, defaultObject: Any = ""): Any? = getExtra(intent?.extras, defaultObject)

        /**
         * Create bundle bundle.
         *
         * @param extraKey the extra key
         * @param extraValue the extra value
         * @return the bundle
         */
        fun createBundle(extraKey: String = INTENT_EXTRA_COMMON, extraValue: Any?): Bundle {
            val bundle = Bundle()
            extraValue?.let {
                when (extraValue) {
                    is Int -> bundle.putInt(extraKey, extraValue)
                    is Long -> bundle.putLong(extraKey, extraValue)
                    is Float -> bundle.putFloat(extraKey, extraValue)
                    is Double -> bundle.putDouble(extraKey, extraValue)
                    is Boolean -> bundle.putBoolean(extraKey, extraValue)
                    is String -> bundle.putString(extraKey, extraValue)
                    is Serializable -> bundle.putSerializable(extraKey, extraValue)
                    else -> bundle.putString(extraKey, extraValue.toString())
                }
            }
            return bundle
        }

        fun getExtra(bundle: Bundle?, extraName: String, defaultObject: Any?): Any? {
            var r = defaultObject
            bundle?.let {
                r = when (defaultObject) {
                    null -> bundle.getSerializable(extraName)
                    is String -> bundle.getString(extraName, defaultObject)
                    is Int -> bundle.getInt(extraName, defaultObject)
                    is Long -> bundle.getLong(extraName, defaultObject)
                    is Float -> bundle.getFloat(extraName, defaultObject)
                    is Double -> bundle.getDouble(extraName, defaultObject)
                    is Boolean -> bundle.getBoolean(extraName, defaultObject)
                    else -> defaultObject
                }
            }
            if (r == null && defaultObject != null) {
                r = defaultObject
            }
            return r
        }
    }
}