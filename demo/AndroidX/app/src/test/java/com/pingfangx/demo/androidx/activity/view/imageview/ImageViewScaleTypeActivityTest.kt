package com.pingfangx.demo.androidx.activity.view.imageview

import org.junit.Test

/**
 *测试缩放
 *
 * @author pingfangx
 * @date 2018/9/20
 */
class ImageViewScaleTypeActivityTest {
    @Test
    fun test() {
        cal(100, 100, 200, 400)
        println()
        cal(100, 100, 50, 100)
    }

    private fun cal(dw: Int = 100, dh: Int = 100, vw: Int = 100, vh: Int = 100) {
        println("dw=$dw\ndh=$dh\nvw=$vw\nvh=$vh")
        val rw = vw.toFloat() / dw
        val rh = vh.toFloat() / dh
        println("vw/dw=$rw\nvh/dh=$rh")
        println("在 fitCenter 时")
        if (rw >= 1 && rh >= 1) {
            println("vw vh 分别大于 dw,dh,scale = 1")
        } else {
            if (rw < rh) {
                println("w 比值较小，以 w 为准，scale = $rw")
            } else {
                println("h 比值较小，以 h 为准，scale = $rh")
            }
        }

        println("在 centerCrop 时")
        println("dw*vh=${dw * vh}\nvw*dh=${vw * dh}")
        /*
        dw*vh>vw*dh
        dw/vw>dh/vh
        vw/dw<vh/dh
        正好与 fitCenter 反着
        也就是说比较 vw/dw 与 vh/dh，fitCenter 取较小者（如果两者都大于1，不缩放）
        centerCrop 取较大者
         */
        if (dw * vh > vw * dh) {
            println("以 h 为准，scale = $rh")
        } else {
            println("以 w 为准，scale = $rw")
        }
    }
}