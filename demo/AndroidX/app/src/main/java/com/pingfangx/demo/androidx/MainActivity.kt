package com.pingfangx.demo.androidx

import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivityListActivity
import dalvik.system.DexFile

/**
 * 一些简单的测试，直接集合到一个 app 里
 */
class MainActivity : BaseActivityListActivity() {
    override fun initActivityList() {
        super.initActivityList()
        findClasses()
    }

    private fun findClasses() {
        val dexFile = DexFile(packageCodePath)
        val entries = dexFile.entries()
        while (entries.hasMoreElements()) {
            val className = entries.nextElement()
            if (className.endsWith("Demo").not()) {
                //先判断一层
                continue
            }
            val clazz = Class.forName(className)
            if (ActivityInitializer::class.java.isAssignableFrom(clazz)) {
                addVirtualActivity(className)
            }
        }
    }
}
