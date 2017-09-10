package com.pingfangx.translator.base

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * 数据管理
 *
 * @author pingfangx
 * @date 2017/9/10
 */
abstract class SpDbHelper<Bean>(context: Context) : IDbHelper<Bean> {

    val mSp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var mList: MutableList<Bean> = mutableListOf()
    private var mHasRead: Boolean = false

    abstract val mSpKey: String

    override fun add(bean: Bean) {
        mList.add(bean)
        saveList(mList)
    }

    override fun remove(bean: Bean) {
        mList.remove(bean)
        saveList(mList)
    }

    override fun list(): List<Bean> {
        if (mHasRead) {
            return mList
        }
        val string: String = mSp.getString(mSpKey, "")
        return listFormString(string)

    }

    override fun clear() {
        mList.clear()
        mSp.edit().remove(mSpKey).apply()
    }

    /**
     * 从字符中解析出数组
     */
    abstract fun listFormString(string: String): List<Bean>

    /**
     * 保存数组
     */
    abstract fun saveList(list: List<Bean>)
}