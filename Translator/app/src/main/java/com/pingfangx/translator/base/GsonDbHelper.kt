package com.pingfangx.translator.base

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * 通过gson解析
 *
 * @author pingfangx
 * @date 2017/9/10
 */
abstract class GsonDbHelper<Bean>(context: Context) : SpDbHelper<Bean>(context) {
    abstract val mType: Type
    private val mGson: Gson = Gson()
    override fun listFormString(string: String): List<Bean> {
        try {
            mList = mGson.fromJson(string, mType)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mList
    }

    override fun saveList(list: List<Bean>) {
        val listString = mGson.toJson(mList)
        mSp.edit().putString(mSpKey, listString).apply()
    }
}