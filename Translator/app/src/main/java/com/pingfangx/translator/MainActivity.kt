package com.pingfangx.translator

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pingfangx.translator.base.BaseViewHolder.OnItemClickListener
import com.pingfangx.translator.databinding.ActivityMainBinding
import com.pingfangx.translator.project.ProjectAdapter
import com.pingfangx.translator.project.ProjectBean
import com.pingfangx.translator.project.ProjectManager
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE_CHOOSE = 1
    }

    private val mProjectManager: ProjectManager by lazy {
        ProjectManager(this)
    }

    val mHasProject = ObservableBoolean()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = this
        initProjectList()
    }

    /**
     * 初始化项目列表
     */
    private fun initProjectList() {
        val list = mProjectManager.list()
        mHasProject.set(list.isEmpty().not())
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adpter = ProjectAdapter(this, list)
        adpter.mOnItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                toast("点击了" + adpter.mData[position].path.get())
            }
        }
        recyclerView.adapter = adpter
    }

    fun onClickBtnAdd(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(intent, "选择目录中的文件"), REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                data?.let {
                    val uri: Uri = data.data
                    val path: String = uri.path
                    //因为选择的是文件，获取目录
                    val parentFile: File = File(path).parentFile
                    val name = parentFile.name
                    mProjectManager.add(ProjectBean(name, parentFile.path))
                    initProjectList()
                }
            }
        }
    }
}
