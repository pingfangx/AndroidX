package com.pingfangx.demo.androidx.activity.android.app.lifecycle

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseTipsActivity
import com.pingfangx.demo.androidx.base.xxlog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

/**
 * 生命周期测试
 *
 * @author pingfangx
 * @date 2019/10/28
 */
abstract class BaseLifecycleActivity : BaseTipsActivity() {
    val name = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "$name onCreate".xxlog()
    }

    override fun onStart() {
        super.onStart()
        "$name onStart".xxlog()
    }

    override fun onResume() {
        super.onResume()
        "$name onResume".xxlog()
    }

    override fun onPause() {
        super.onPause()
        "$name onPause".xxlog()
    }

    override fun onStop() {
        super.onStop()
        "$name onStop".xxlog()
    }

    override fun onDestroy() {
        super.onDestroy()
        "$name onDestroy".xxlog()
    }

    override fun onRestart() {
        super.onRestart()
        "$name onRestart".xxlog()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "$name onNewIntent".xxlog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        "$name onActivityResult $resultCode".xxlog()
    }

    override fun initViews() {
        super.initViews()
        val linearlayout: LinearLayout = findViewById(R.id.ll_root)
        addButton<AActvity>(linearlayout)
        addButton<BActvity>(linearlayout)
        addButton(linearlayout, "启动 B FLAG_ACTIVITY_NEW_TASK", View.OnClickListener {
            startActivity(
                    Intent(it.context, BActvity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        })
        addButton(linearlayout, "启动 B FLAG_ACTIVITY_SINGLE_TOP", View.OnClickListener {
            startActivity(
                    Intent(it.context, BActvity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        })
        addButton(linearlayout, "启动 B FLAG_ACTIVITY_CLEAR_TOP", View.OnClickListener {
            startActivity(
                    Intent(it.context, BActvity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        })
        addButton(linearlayout, "显示对话框", View.OnClickListener {
            AlertDialog.Builder(this@BaseLifecycleActivity)
                    .setPositiveButton("确认", null)
                    .show()
        })
        addButton<StandardActivity>(linearlayout)
        addButton<SingleTopActivity>(linearlayout)
        addButton<SingleTaskActivity>(linearlayout)
        addButton<SingleTaskWithAffinityActivity>(linearlayout)
        addButton<SingleInstanceActivity>(linearlayout)


        addButtonForResult<StandardActivity>(linearlayout)
        addButtonForResult<SingleTopActivity>(linearlayout)
        addButtonForResult<SingleTaskActivity>(linearlayout)
        addButtonForResult<SingleInstanceActivity>(linearlayout)
        addButton(linearlayout, "setResult And Finish", View.OnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        })

    }

    private inline fun <reified T : Activity> addButton(viewGroup: ViewGroup) {
        addButton(viewGroup, "startActivity " + T::class.java.simpleName, View.OnClickListener { startActivity<T>() })
    }

    private inline fun <reified T : Activity> addButtonForResult(viewGroup: ViewGroup) {
        addButton(viewGroup, "startActivityForResult " + T::class.java.simpleName, View.OnClickListener { startActivityForResult<T>(1) })
    }

    private fun addButton(viewGroup: ViewGroup, text: CharSequence, onClickListener: View.OnClickListener) {
        val btn = Button(this)
        btn.isAllCaps = false
        btn.text = text
        btn.setOnClickListener(onClickListener)
        viewGroup.addView(btn)
    }
}

class AActvity : BaseLifecycleActivity()

class BActvity : BaseLifecycleActivity()
class StandardActivity : BaseLifecycleActivity()
class SingleTopActivity : BaseLifecycleActivity()
class SingleTaskActivity : BaseLifecycleActivity()
class SingleTaskWithAffinityActivity : BaseLifecycleActivity()
class SingleInstanceActivity : BaseLifecycleActivity()