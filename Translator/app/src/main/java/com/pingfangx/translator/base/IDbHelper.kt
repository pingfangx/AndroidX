package com.pingfangx.translator.base

/**
 * 数据管理
 *
 * @author pingfangx
 * @date 2017/9/10
 */
interface IDbHelper<Bean> {

    /**
     * 添加
     */
    fun add(bean: Bean)

    /**
     * 移除
     */
    fun remove(bean: Bean)

    /**
     * 列出
     */
    fun list(): List<Bean>

    /**
     * 清空
     */
    fun clear()
}