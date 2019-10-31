package com.pingfangx.demo.androidx.base.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import org.jetbrains.anko.toast
import java.security.MessageDigest

/**
 * 工具的扩展
 *
 * @author pingfangx
 * @date 2018/10/8
 */

/**
 * 获取支持的 abi
 */
fun getSupportedAbi(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //adb shell getprop ro.product.cpu.abilist
        Build.SUPPORTED_ABIS.joinToString(",")
    } else {
        //adb shell getprop ro.product.cpu.abi
        @Suppress("DEPRECATION")//已经判断版本号
        Build.CPU_ABI + "," + Build.CPU_ABI2
    }
}

/**
 * 复制到剪贴板
 */
fun Context.copyToClipboard(text: CharSequence, toast: Boolean = true) {
    val clipboardManager: ClipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(text, text))
    if (toast) {
        this.toast("已复制\n$text")
    }
}

fun getAppSignature(context: Context, packageName: String): String {
    val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val packageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        val signingInfo = packageInfo.signingInfo
        if (signingInfo.hasMultipleSigners()) {
            signingInfo.apkContentsSigners
        } else {
            signingInfo.signingCertificateHistory
        }
    } else {
        val packageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        packageInfo.signatures
    }
    return if (signatures != null && signatures.isNotEmpty()) {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(signatures[0].toByteArray())
        toHexString(md5.digest())
    } else {
        ""
    }
}

/**
 * @param upper 是否转为大写
 * @param addSeparator 添加分隔符，如可以添加 :
 */
fun toHexString(bytes: ByteArray, upper: Boolean = true, addSeparator: CharSequence? = null): String {
    val stringBuilder = StringBuilder(bytes.size * 2)
    for (i in bytes.indices) {
        val b = bytes[i]
        var s = (0xFF and b.toInt()).toString(16)
        if (upper) {
            s = s.toUpperCase()
        }
        if (addSeparator != null && i > 0) {
            stringBuilder.append(addSeparator)
        }
        if (s.length == 1) {
            stringBuilder.append('0')
        }
        stringBuilder.append(s)
    }
    return stringBuilder.toString()
}

fun toHexString2(bytes: ByteArray): String {
    val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    val result = CharArray(bytes.size shl 1)
    var i = 0
    for (b in bytes) {
        val m = b.toInt()
        //前 4 位
        result[i++] = hexDigits[(0xF0 and m) ushr 4]
        //后 4 位
        result[i++] = hexDigits[0x0F and m]
    }
    return String(result)
}
