package com.pingfangx.translator

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.pingfangx.translator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val mHasProject = ObservableBoolean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.setVm(this)
    }

    fun onClickBtnAdd(view: View) {
        this.toast("点击了添加")
    }
}
