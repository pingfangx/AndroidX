package com.pingfangx.demo.androidx.activity.tool.program

import com.pingfangx.demo.androidx.base.BaseTipsActivity
import com.pingfangx.demo.androidx.base.extension.getSupportedAbi

/**
 * Abi 与微架构
 *
 * @author pingfangx
 * @date 2018/10/8
 */
class AbiAndMicroarchitectureActivity : BaseTipsActivity() {
    override fun getTips(): CharSequence {
        return "SUPPORTED_ABIS:\n" + getSupportedAbi()
    }

}