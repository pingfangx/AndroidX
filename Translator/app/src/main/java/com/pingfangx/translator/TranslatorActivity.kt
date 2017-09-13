package com.pingfangx.translator

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import com.pingfangx.translator.base.BaseActivity
import com.pingfangx.translator.base.IntentUtils
import kotlinx.android.synthetic.main.activity_translator.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class TranslatorActivity : BaseActivity() {
    /**
     * 分隔符
     */
    private val SEPARATOR = "[xx|]"
    private val REQUEST_PERMISSION = 1

    private val mProjectPath: String by lazy {
        IntentUtils.getExtra(intent) as String
    }
    private val mTranslationFile: String by lazy {
        File(mProjectPath + "/source").list()[0]
    }
    private val mSource: MutableList<String> by lazy {
        val firstFile = "$mProjectPath/source/$mTranslationFile"
        getTranslation(firstFile)
    }
    private val mTarget: MutableList<String> by lazy {
        val firstFile = "$mProjectPath/target/$mTranslationFile"
        getTranslation(firstFile)
    }
    private val mDictionary: MutableList<String> by lazy {
        val folder = File(mProjectPath + "/tm")
        val firstFile = folder.path + "/" + folder.list()[0]
        getTranslation(firstFile)
    }
    private val mTargetFile: String by lazy {
        "$mProjectPath/target/$mTranslationFile"
    }

    private var mIndex: Int = 0

    private var mCurrentKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translator)
        val perms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (EasyPermissions.hasPermissions(this, *perms)) {
            initViews()
        } else {
            EasyPermissions.requestPermissions(this, "请允许读取文件权限", REQUEST_PERMISSION, *perms)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        super.onPermissionsGranted(requestCode, perms)
        initViews()
    }

    private fun initViews() {
        var index = 0
        if (mTarget.isEmpty().not()) {
            for (i in 0 until mTarget.size) {
                if (mSource[i] == mTarget[i]) {
                    if (mSource[i].split('=').size > 1) {
                        index = i
                        break
                    }
                }
            }
        }
        showTranslation(index)
        btn_pre.setOnClickListener({ showTranslation(mIndex - 1) })
        btn_next.setOnClickListener({ showTranslation(mIndex + 1) })
        btn_save.setOnClickListener { saveTranslation(et_translation.text.toString()) }
        btn_save_empty.setOnClickListener { saveTranslation("") }
        et_translation.setSingleLine(true)
        et_translation.inputType = EditorInfo.TYPE_CLASS_TEXT
        et_translation.setOnEditorActionListener({ _, i, _ ->
            if (i == EditorInfo.IME_ACTION_GO) {
                saveTranslation(et_translation.text.toString())
                true
            } else {
                false
            }
        })
        radio_group.setOnCheckedChangeListener { _, i -> saveTranslation((radio_group.findViewById(i) as RadioButton).text.toString()) }
    }

    private fun showTranslation(index: Int) {
        if (index < 0 || index >= mSource.size) {
            return
        }

        //找到有效的一行
        mIndex = index
        while (mIndex >= 0 && mIndex < mSource.size) {
            val line: String = mSource[mIndex]
            val keyAndValue = line.split("=")
            if (keyAndValue.size < 2) {
                "line at $mIndex wrong".log()
                mIndex += 1
                continue
            }
            break
        }

        val line: String = mSource[mIndex]
        val keyAndValue = line.split("=")
        //设置行号
        tv_index.text = "${mIndex + 1}/${mSource.size}"
        val sourceKey: String = keyAndValue[0]
        val sourceValue: String = keyAndValue[1]

        mCurrentKey = sourceKey
        tv_english.text = sourceValue

        val mCurrentValueArray: MutableList<String> = mutableListOf()

        //添加词典
        for (iLine: String in mDictionary) {
            //用startsWith减少判断次数，=容易满足
            if (iLine.startsWith(sourceValue)) {
                val splitResult = iLine.split("=")
                if (splitResult.size > 1) {
                    if (splitResult[0] == sourceValue) {
                        //找到
                        mCurrentValueArray += splitResult[1].split(SEPARATOR)
                        break
                    }
                }
            }
        }
        //添加已翻译的
        for (iLine: String in mTarget) {
            val splitResult = iLine.split("=")
            if (splitResult.size > 1) {
                if (splitResult[0] == sourceKey) {
                    if (splitResult[1] != sourceValue) {
                        mCurrentValueArray.add(0, splitResult[1])
                        break
                    }
                }
            }
        }

        if (mCurrentValueArray.isEmpty().not()) {
            et_translation.setText(mCurrentValueArray[0])
        } else {
            et_translation.setText("")
        }
        radio_group.removeAllViews()
        for (i in 0 until mCurrentValueArray.size) {
            val radioButton = RadioButton(this)
            radioButton.id = i
            radioButton.text = mCurrentValueArray[i]
            radioButton.height = 100
            radio_group.addView(radioButton)
        }
    }

    private fun getTranslation(filePath: String): MutableList<String> {
        if (File(filePath).exists().not()) {
            return mutableListOf()
        }
        val fileReader = FileReader(filePath)
        val lindes = fileReader.readLines()
        fileReader.close()
        return lindes.toMutableList()
    }

    private fun saveTranslation(translation: String) {
        if (mTarget.isEmpty()) {
            mTarget.addAll(mSource)
        }
        mTarget[mIndex] = mCurrentKey + "=" + translation
        saveTranslationToFile(mTargetFile, mTarget)
        showTranslation(mIndex + 1)
    }

    private fun saveTranslationToFile(filePath: String, list: List<String>) {
        val parentFile = File(filePath).parentFile
        if (parentFile.exists().not()) {
            parentFile.mkdirs()
        }
        val fileWriter = FileWriter(mTargetFile)
        fileWriter.write(list.joinToString("\n"))
        fileWriter.close()
        "write success $filePath".log()
    }
}
