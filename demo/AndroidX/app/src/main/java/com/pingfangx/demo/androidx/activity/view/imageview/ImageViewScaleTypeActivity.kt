package com.pingfangx.demo.androidx.activity.view.imageview

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseListActivity
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewHolder

/**
 * 图片 ScaleType
 *
 * @author pingfangx
 * @date 2018/9/19
 */
class ImageViewScaleTypeActivity : BaseListActivity() {
    private val mData: MutableList<ScaleTypeBean> = mutableListOf()

    override fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<*> {
        mData.add(ScaleTypeBean(ImageView.ScaleType.MATRIX,"绘图时使用图像矩阵缩放。可以使用 setImageMatrix(Matrix) 设置图像矩阵。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.FIT_XY, "独立地在 X 和 Y 中缩放，以便 src 完全匹配 dst。这可能会改变 src 的宽高比。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.FIT_START, "计算将保持原始 src 宽高比的比例，但也将确保 src 完全适合 dst。至少一个轴(X 或 Y)将精确匹配。START 将结果与 dst 的左边和上边对齐。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.FIT_CENTER, "计算将保持原始 src 宽高比的比例，但也将确保 src 完全适合 dst。至少一个轴(X 或 Y)将精确匹配。结果在 dst 内的中间。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.FIT_END, "计算将保持原始 src 宽高比的比例，但也将确保 src 完全适合 dst。至少一个轴(X 或 Y)将精确匹配。END 将结果与 dst 的右边和底边对齐。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.CENTER,"使图像在 view 中居中，但不执行缩放。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.CENTER_CROP,"均匀缩放图像(保持图像的纵横比)，使图像的尺寸(宽度和高度)等于或大于 view 的相应尺寸(减去 padding)。然后图像在 view 中居中。\n测试时发现，如可图片宽高都大于，会进行缩小，即也有至少一个轴(X 或 Y)将精确匹配。"))
        mData.add(ScaleTypeBean(ImageView.ScaleType.CENTER_INSIDE,"均匀缩放图像(保持图像的纵横比)，使图像的尺寸(宽度和高度)等于或小于 view 的相应尺寸(减去 padding)。然后图像在 view 中居中。"))
        return ScaleTypeAdapter(this, mData)
    }
}

class ScaleTypeAdapter(mContext: Context, mData: List<ScaleTypeBean>) : BaseRecyclerViewAdapter<ScaleTypeBean>(mContext, mData) {
    override fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<ScaleTypeBean> {
        return ScaleTypeViewHolder(itemView)
    }

    override fun getLayoutResId() = R.layout.item_image_view_scale_type
}

class ScaleTypeViewHolder(itemView: View) : BaseRecyclerViewHolder<ScaleTypeBean>(itemView) {
    private val mTvTitle: TextView = itemView.findViewById(R.id.tv_title)
    private val mIv0: ImageView = itemView.findViewById(R.id.iv_image_0)
    private val mIv1: ImageView = itemView.findViewById(R.id.iv_image_1)
    private val mIv2: ImageView = itemView.findViewById(R.id.iv_image_2)

    init {
        mIv0.setImageResource(R.mipmap.ic_launcher)
        mIv1.setImageResource(R.mipmap.ic_launcher)
        mIv2.setImageResource(R.mipmap.ic_launcher)
    }

    override fun bindTo(t: ScaleTypeBean, position: Int) {
        mTvTitle.text = t.toString()
        mIv0.scaleType = t.scaleType
        mIv1.scaleType = t.scaleType
        mIv2.scaleType = t.scaleType
    }
}

data class ScaleTypeBean(val scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER, val tips: String = "") {
    override fun toString(): String {
        return "$scaleType\n$tips"
    }
}