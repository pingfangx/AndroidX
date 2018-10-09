package com.pingfangx.demo.androidx.activity.tool.program

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseListActivity
import com.pingfangx.demo.androidx.base.extension.copyToClipboard
import com.pingfangx.demo.androidx.base.extension.getAppSignature
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewHolder
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextViewHolder
import org.jetbrains.anko.contentView

/**
 * app 列表
 *
 * @author pingfangx
 * @date 2018/10/8
 */
class AppListActivity : BaseListActivity() {
    /**
     * 所有的 app
     */
    private var mAppList: List<PackageInfo> = emptyList()

    /**
     * 展示的 app
     */
    private var mFilterAppList: MutableList<PackageInfo> = mutableListOf()
    private val mAppAdapter = AppAdapter(this, mFilterAppList)

    override fun createAdapter(): RecyclerView.Adapter<*> {
        mAppList = getAppList()
        mFilterAppList = filterApp("")
        mAppAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<PackageInfo> {
            override fun onItemClick(view: View, position: Int, t: PackageInfo) {
                onItemClick(t)
            }
        })
        return mAppAdapter
    }

    private fun onItemClick(packageInfo: PackageInfo) {
        val items = resources.getStringArray(R.array.dialog_app_list_action_array)
        val dialog = AlertDialog.Builder(this)
                .setTitle("${packageInfo.packageLabel}(${packageInfo.packageName})")
                .setItems(items) { _, which -> onClickDialogAction(packageInfo, items[which]) }
                .create()
        dialog.show()
    }

    private fun onClickDialogAction(packageInfo: PackageInfo, action: String) {
        if (action == getString(R.string.dialog_app_list_action_copy_package_name)) {
            copyToClipboard(packageInfo.packageName)
        } else if (action == getString(R.string.dialog_app_list_action_copy_signature)) {
            copyToClipboard(packageInfo.signature)
        }
    }

    override fun initViews() {
        super.initViews()
        val contentView = contentView
        if (contentView is ViewGroup) {
            val editText = EditText(this)
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    filterApp(s.toString())
                    mAppAdapter.notifyDataSetChanged()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            contentView.addView(editText, 0, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200))
        }
    }

    /**
     * 过滤 app
     */
    private fun filterApp(string: String): MutableList<PackageInfo> {
        mFilterAppList.clear()
        if (string.isEmpty()) {
            mFilterAppList.addAll(mAppList)
        } else {
            mFilterAppList.addAll(mAppList.filter {
                it.packageName.contains(string) or it.packageLabel.contains(string)
            })
        }
        return mFilterAppList
    }

    /**
     * 所有非官方 app
     * 涉及部分机型需要权限，则参照
     * [Android 如何完整的获取到用户已安装应用列表](https://blog.csdn.net/q384415054/article/details/72972405)
     */
    private fun getAppList(): List<PackageInfo> {
        val packageManager = packageManager
        val packages = packageManager.getInstalledPackages(0)

        val result = mutableListOf<PackageInfo>()
        for (packageInfo in packages) {
            if ((packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                result.add(PackageInfo(packageInfo))
            }
        }
        return result
    }

    /**
     * 使用幕后属性，只加载一次，便于过滤
     */
    private inner class PackageInfo(val _packageInfo: android.content.pm.PackageInfo) {

        val packageName: String
            get() {
                return _packageInfo.packageName
            }

        private var _packageLabel: CharSequence = ""
        val packageLabel: CharSequence
            get() {
                if (_packageLabel.isEmpty()) {
                    _packageLabel = _packageInfo.applicationInfo.loadLabel(packageManager)
                }
                return _packageLabel
            }

        val icon: Drawable?
            get() {
                //不使用幕后字段持有
                return _packageInfo.applicationInfo.loadIcon(packageManager)
            }

        private var _signature: String = ""
        val signature: String
            get() {
                if (_signature.isEmpty()) {
                    //不能直接用 _packageInfo
                    _signature = getAppSignature(this@AppListActivity, this.packageName)
                }
                return _signature
            }
    }

    private inner class AppAdapter(mContext: Context, mData: List<PackageInfo>) : BaseTextAdapter<PackageInfo>(mContext, mData) {
        override fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<PackageInfo> {
            return AppViewHolder(itemView)
        }

        override fun getLayoutResId(): Int {
            return R.layout.item_app
        }
    }

    private inner class AppViewHolder(itemView: View) : BaseTextViewHolder<PackageInfo>(itemView) {
        private val mIvImage: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.iv_image)
        }
        private val mTvDesc: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_desc)
        }

        override fun bindTo(t: PackageInfo, position: Int) {
            // app 名
            super.bindTo(t, position)
            // 包名
            mTvDesc.text = t.packageName
            // 图标
            mIvImage.setImageDrawable(t.icon)
        }

        override fun getText(t: PackageInfo): CharSequence {
            return t.packageLabel
        }
    }
}