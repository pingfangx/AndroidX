package com.pingfangx.demo.androidx.base

import org.junit.Test

/**
 * 扩展测试
 *
 * @author pingfangx
 * @date 2018/7/2
 */
class ExtensionTest {

    @Test
    fun camelToUnderlineTest() {
        println("".camelToUnderline())
        println("ABC".camelToUnderline())
        println("TestActivity".camelToUnderline())
        println("TestBActivity".camelToUnderline())
        println("TestB_Activity".camelToUnderline())
    }
}