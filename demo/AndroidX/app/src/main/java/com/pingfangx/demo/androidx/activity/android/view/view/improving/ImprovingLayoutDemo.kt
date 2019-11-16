package com.pingfangx.demo.androidx.activity.android.view.view.improving

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.addButton

/**
 * include merge ViewStub
 *
 * @author pingfangx
 * @date 2019/11/3
 */
class ImprovingLayoutDemo : ActivityLifecycle {
    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        activity.addButton("include", View.OnClickListener {
            activity.setContentView(R.layout.activity_improving_include_demo)
        })
        activity.addButton("merge", View.OnClickListener {
            activity.setContentView(R.layout.activity_improving_merge_demo)
        })
        activity.addButton("ViewStub", View.OnClickListener {
            activity.setContentView(R.layout.activity_improving_viewstub_demo)
            activity.findViewById<View>(R.id.fl_container).setOnClickListener {
                it.setOnClickListener(null)
                activity.findViewById<ViewStub>(R.id.view_stub).visibility = View.VISIBLE
            }
        })
    }
}