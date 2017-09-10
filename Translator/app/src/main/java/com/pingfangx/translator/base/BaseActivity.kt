package com.pingfangx.translator.base

import android.support.v7.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions

/**
 * 基类Activity
 *
 * @author pingfangx
 * @date 2017/9/10
 */
open class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}