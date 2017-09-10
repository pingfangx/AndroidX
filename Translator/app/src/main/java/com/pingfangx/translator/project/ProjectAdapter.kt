package com.pingfangx.translator.project

import android.content.Context
import android.databinding.ViewDataBinding
import com.pingfangx.translator.R
import com.pingfangx.translator.base.BaseDataBindingAdapter
import com.pingfangx.translator.base.BaseDataBindingViewHolder
import com.pingfangx.translator.databinding.ItemProjectBinding

/**
 * 项目的Adapter
 *
 * @author pingfangx
 * @date 2017/9/10
 */
class ProjectAdapter(context: Context, data: List<ProjectBean>) : BaseDataBindingAdapter<ProjectBean, ProjectAdapter.ProjectViewHoder>(context, data) {
    override fun onCreateViewHolder(binding: ViewDataBinding): ProjectViewHoder = ProjectViewHoder(binding)

    override fun getItemLayoutResId(): Int = R.layout.item_project

    class ProjectViewHoder(binding: ViewDataBinding) : BaseDataBindingViewHolder<ProjectBean>(binding) {
        override fun bindTo(bean: ProjectBean) {
            if (mBinding is ItemProjectBinding) {
                mBinding.bean = bean
            }
        }
    }
}