package com.pingfangx.demo.androidx.activity.android.content.provider

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.xxlog
import org.jetbrains.anko.toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 以照片为示例
 *
 * @author pingfangx
 * @date 2019/10/30
 */
class FileProviderDemo : ActivityLifecycle {
    companion object {
        const val REQUEST_CODE_TAKE_PHOTO = 1
    }

    private var mPhotoUri: Uri? = null
    private var mPhotoFile: File? = null

    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        //指定路径不需要权限，可以在 manifest 中配置 android:maxSdkVersion="18"
        activity.addButton("拍照指定路径", View.OnClickListener { takePhoto(activity, true) })
        //插入需要权限，uri 为 content://media/external/images/media/<\d+>，保存到 /sdcard/Pictures
        activity.addButton("拍照插入 uri", View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_TAKE_PHOTO)
            } else {
                takePhoto(activity, false)
            }
        })
    }

    private fun takePhoto(activity: BaseActivity, useFile: Boolean) {
        if (activity.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY).not()) {
            activity.toast("没有相机")
            return
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(activity.packageManager) ?: return

        val photoUri: Uri
        if (useFile) {
            //因为保存到 getExternalFilesDir，不需要存储权限
            val photoFile = createPhotoFile(activity) ?: return
            photoUri = FileProvider.getUriForFile(activity, activity.getString(R.string.com_pingfangx_demo_androidx_fileprovider), photoFile)
            "发起拍照，指定的路径为 ${photoFile.absolutePath}".also {
                activity.toast(it)
                it.xxlog()
            }
            mPhotoFile = photoFile
            mPhotoUri = null
        } else {
            //通获插入获得 uri，最后通过 uri query 获得路径
            photoUri = activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
                    ?: return
            "发起拍照,指定的 uri 为 $photoUri".also {
                activity.toast(it)
                it.xxlog()
            }
            mPhotoFile = null
            mPhotoUri = photoUri
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
    }

    private fun addGalleryPic(context: Context, path: String) {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            it.data = Uri.fromFile(File(path))
            context.sendBroadcast(it)
        }
    }

    override fun onRequestPermissionsResult(activity: BaseActivity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(activity, requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            permissions.forEachIndexed { index, s ->
                if (s == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        takePhoto(activity, false)
                    }
                }
            }
        }
    }

    override fun onActivityResult(activity: BaseActivity, requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(activity, requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                val photoFile = mPhotoFile
                if (photoFile != null) {
                    //从文件读取
                    activity.toast("拍照成功 ${photoFile.absolutePath}")
                    addGalleryPic(activity, photoFile.absolutePath)
                    mPhotoFile = null
                } else {
                    //从 uri 读取
                    val photoUri = mPhotoUri
                    if (photoUri != null) {
                        val cursor = activity.contentResolver.query(photoUri, null, null, null, null)
                        cursor ?: return
                        if (cursor.moveToFirst()) {
                            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                            activity.toast("拍照成功 ${path}")
                            addGalleryPic(activity, path)
                        }
                        cursor.close()
                        mPhotoUri = null
                    }
                }

            }
        }
    }

    private fun createPhotoFile(context: Context): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir ?: return null
        return File.createTempFile("PHOTO_${timeStamp}_", ".jpg", storageDir)
    }
}