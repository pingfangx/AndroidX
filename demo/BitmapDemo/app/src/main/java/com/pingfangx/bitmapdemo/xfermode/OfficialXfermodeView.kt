package com.pingfangx.bitmapdemo.xfermode

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * 官方 demo 转为 kotlin
 *
 * 代码、命名基本没变，就是修改了逻辑
 *
 * 相关博文:[《[2532]Xfermode》](http://blog.pingfangx.com/2532.html)
 *
 * @author pingfangx
 * @date 2018/5/10
 */

open class OfficialXfermodeView : View {


    /**
     * 源图
     */
    var mSrcB: Bitmap? = null
    /**
     * 目标图
     */
    var mDstB: Bitmap? = null
    /**
     * 背景
     */
    private val mBG: Shader     // background checker-board pattern

    /**
     * 标签 Paint
     */
    val labelP: Paint
    /**
     * 绘图 Paint
     */
    val paint: Paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        //创建背景
        // make a ckeckerboard pattern
        val bm = Bitmap.createBitmap(intArrayOf(-0x1, -0x333334, -0x333334, -0x1), 2, 2,
                Bitmap.Config.RGB_565)
        mBG = BitmapShader(bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT)
        val m = Matrix()
        m.setScale(6F, 6F)
        mBG.setLocalMatrix(m)

        //初始化 Paint
        labelP = Paint(Paint.ANTI_ALIAS_FLAG)
        labelP.textAlign = Paint.Align.CENTER
        labelP.textSize = TEXT_SIZE

        paint = Paint()
        paint.isFilterBitmap = false
    }

    /**
     * create a bitmap with a circle, used for the "dst" image
     */
    open fun makeDst(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = -0x33bc
        //圆占左上部分，3/4
        c.drawOval(RectF(0f, 0f, (w * 3 / 4).toFloat(), (h * 3 / 4).toFloat()), p)
        return bm
    }

    /**
     * create a bitmap with a rect, used for the "src" image
     */
    open fun makeSrc(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = -0x995501
        //正方形占右下部分，1/3-19/20
        c.drawRect(w / 3F, h / 3F, w * 19 / 20F, h * 19 / 20F, p)
        return bm
    }

    override fun onDraw(canvas: Canvas) {

        //应该在 initView 中设置，写在这里作为提醒
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        //创建
        if (mSrcB == null) {
            mSrcB = makeSrc(W, H)
        }
        if (mDstB == null) {
            mDstB = makeDst(W, H)
        }

        if (drawBackground()) {
            canvas.drawColor(Color.WHITE)
        }


        canvas.translate(15F, 35F)


        var x = 0F
        //介绍
        canvas.drawText(getDescription(), x + W / 2, y - labelP.textSize / 2, labelP)
        var y = labelP.textSize
        val modes = PorterDuff.Mode.values()
        for (i in modes.indices) {
            val mode = modes[i]

            //绘制背景
            if (drawBackground()) {
                // draw the border
                paint.style = Paint.Style.STROKE
                paint.shader = null
                canvas.drawRect(x - 0.5f, y - 0.5f,
                        x + W.toFloat() + 0.5f, y + H.toFloat() + 0.5f, paint)

                // draw the checker-board pattern
                paint.style = Paint.Style.FILL
                paint.shader = mBG
                canvas.drawRect(x, y, x + W, y + H, paint)
            }

            //绘制内容
            drawContent(canvas, x, y, mode)

            // draw the label
//            canvas.drawText(i.toString() + '-' + mode.name,
//                    x + W / 2, y - labelP.textSize / 2, labelP)

            x += W + 10

            // wrap around when we've drawn enough for one row
            if ((i % ROW_MAX) == ROW_MAX - 1) {
                x = 0F
                y += H + SPAN
            }
        }
    }

    /**
     * 绘制内容，先绘制 dst 再绘制 src
     */
    open fun drawContent(canvas: Canvas, x: Float, y: Float, mode: PorterDuff.Mode) {
        // draw the src/dst example into our offscreen bitmap
        @Suppress("DEPRECATION")
        @SuppressLint("WrongConstant")
        val sc = canvas.saveLayer(x, y, x + W, y + H, null,
                Canvas.MATRIX_SAVE_FLAG or
                        Canvas.CLIP_SAVE_FLAG or
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG or
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG or
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG)
        canvas.translate(x, y)
        canvas.drawBitmap(mDstB, 0F, 0F, paint)

        @SuppressLint("DrawAllocation")
        paint.xfermode = PorterDuffXfermode(mode)
        canvas.drawBitmap(mSrcB, 0F, 0F, paint)
        paint.xfermode = null
        canvas.restoreToCount(sc)
    }

    /**
     * 标题描述
     */
    open fun getDescription(): String {
        return "1 官方 Demo"
    }

    /**
     * 是否绘制背景
     */
    open fun drawBackground(): Boolean {
        return true
    }

    /**
     * 仅测试
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = W + 10
        val h = (H + SPAN) * PorterDuff.Mode.values().size
        setMeasuredDimension(w, h.toInt())
    }

    companion object {
        const val W = 150
        const val H = W
        const val ROW_MAX = 1   // number of samples per row
        const val TEXT_SIZE = 25F
        const val SPAN = 60F
    }
}