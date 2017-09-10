package com.pingfangx.translator.base

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 通过gson解析
 *
 * @author pingfangx
 * @date 2017/9/10
 */
abstract class GsonDbHelper<Bean>(context: Context) : SpDbHelper<Bean>(context) {

    private val mGson: Gson = Gson()
    override fun listFormString(string: String): List<Bean> {
        val type = object : TypeToken<List<Bean>>() {

        }.type
        try {
            mList = mGson.fromJson(string, type)
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