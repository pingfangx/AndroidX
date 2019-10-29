package com.pingfangx.demo.androidx.activity.androidx.fragment.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pingfangx.demo.androidx.base.xxlog

/**
 * 生命周期测试
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class LifecycleFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        "fragment onAttach".xxlog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "fragment onCreate".xxlog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        "fragment onCreateView".xxlog()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "fragment onViewCreated".xxlog()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        "fragment onActivityCreated".xxlog()
    }

    override fun onStart() {
        super.onStart()
        "fragment onStart".xxlog()
    }

    override fun onResume() {
        super.onResume()
        "fragment onResume".xxlog()
    }

    override fun onPause() {
        super.onPause()
        "fragment onPause".xxlog()
    }

    override fun onStop() {
        super.onStop()
        "fragment onStop".xxlog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "fragment onDestroyView".xxlog()
    }

    override fun onDestroy() {
        super.onDestroy()
        "fragment onDestroy".xxlog()
    }

    override fun onDetach() {
        super.onDetach()
        "fragment onDetach".xxlog()
    }
}