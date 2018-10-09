package com.pingfangx.demo.androidx.base.extension

import org.junit.Test
import java.security.MessageDigest

/**
 *
 * @author pingfangx
 * @date 2018/10/9
 */
class ToolsExtensionTest {

    @Test
    fun testToHexString() {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update("A".toByteArray())
        val digest = md5.digest()
        println(toHexString(digest))
        println(toHexString(digest, upper = false))
        println(toHexString(digest, addSeparator = ":"))
        println(toHexString2(digest))
    }
}