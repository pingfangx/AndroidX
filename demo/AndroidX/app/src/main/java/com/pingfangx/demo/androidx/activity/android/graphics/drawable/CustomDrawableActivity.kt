package com.pingfangx.demo.androidx.activity.android.graphics.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.pingfangx.demo.androidx.base.BaseTipsActivity
import com.pingfangx.demo.androidx.base.extension.setBackgroundDrawable
import kotlinx.android.synthetic.main.activity_base_tips.*

/**
 * 自定义 Drawable
 *
 * @author pingfangx
 * @date 2018/12/25
 */
class CustomDrawableActivity : BaseTipsActivity() {
    override fun initViews() {
        super.initViews()
        val tv = TextView(this)
        tv.setTextColor(Color.RED)
        tv.text = "居中"
        tv.gravity = Gravity.CENTER
        setBackgroundDrawable(tv, BubbleDrawable())
        val layoutParams = LinearLayout.LayoutParams(500, 500)
        layoutParams.leftMargin = 50
        ll_container.addView(tv, 0, layoutParams)
    }
}

class BubbleDrawable : ShapeDrawable() {
    private val mTrianglePath = Path()

    init {
        val radius = 100F
        val outerRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        shape = RoundRectShape(outerRadii, null, null)
        paint.color = Color.BLUE
        //设置 paddingTop 使文字居中
        setPadding(0, getTriangleHeight().toInt(), 0, 0)
    }

    private fun getTriangleWidth(): Float = 100F
    private fun getTriangleHeight(): Float = 100F

    override fun draw(canvas: Canvas) {
        //绘制三角
        canvas.drawPath(mTrianglePath, paint)
        val count = canvas.save()
        //留出箭头范围
        canvas.translate(0F, getTriangleHeight())
        super.draw(canvas)
        canvas.restoreToCount(count)
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        if (bounds != null) {
            val w = bounds.width()
            val h = bounds.height()
            //调整 shape 大小，高度减小箭头高度
            shape.resize(w.toFloat(), h - getTriangleHeight())
            updateTrianglePath(w, h)
        }
    }

    /**
     * 更新三角
     */
    private fun updateTrianglePath(w: Int, @Suppress("UNUSED_PARAMETER") h: Int) {
        val start: Float = w - getTriangleWidth() - 100
        mTrianglePath.rewind()
        mTrianglePath.moveTo(start, getTriangleHeight())
        mTrianglePath.lineTo(start + getTriangleWidth() / 2, 0F)
        mTrianglePath.lineTo(start + getTriangleWidth(), getTriangleHeight())
        mTrianglePath.close()
    }

}