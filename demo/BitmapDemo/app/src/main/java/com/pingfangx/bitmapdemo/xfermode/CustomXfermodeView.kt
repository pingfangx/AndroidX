package com.pingfangx.bitmapdemo.xfermode

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.pingfangx.bitmapdemo.R

/**
 *
 * 相关博文:[《[2532]Xfermode》](http://blog.pingfangx.com/2532.html)
 * @author pingfangx
 * @date 2018/5/2
 */

open class CustomXfermodeView2 : OfficialXfermodeView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    /**
     * 创建大小相等、透明的图
     */
    override fun makeSrc(w: Int, h: Int): Bitmap {
        return makeBitmap(w, h, R.drawable.ic_source_transparent)
    }

    override fun makeDst(w: Int, h: Int): Bitmap {
        return makeBitmap(w, h, R.drawable.ic_destination_transparent)
    }

    fun makeBitmap(w: Int, h: Int, resId: Int): Bitmap {
        val result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        val src = Rect(0, 0, bitmap.width, bitmap.height)
        val dst = Rect(0, 0, w, h)
        canvas.drawBitmap(bitmap, src, dst, paint)
        return result
    }

    override fun getDescription(): String {
        return "2 官方 api"
    }
}

class CustomXfermodeView3 : CustomXfermodeView2 {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun drawContent(canvas: Canvas, x: Float, y: Float, mode: PorterDuff.Mode) {
        canvas.drawBitmap(mDstB, x, y, paint)
        @SuppressLint("DrawAllocation")
        paint.xfermode = PorterDuffXfermode(mode)
        canvas.drawBitmap(mSrcB, x, y, paint)
        paint.xfermode = null
    }

    override fun getDescription(): String {
        return "3 不 saveLayer"
    }
}

open class CustomXfermodeView4 : CustomXfermodeView2 {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 创建大小相等、但是不透明的图
     */
    override fun makeSrc(w: Int, h: Int): Bitmap {
        return makeBitmap(w, h, R.drawable.ic_source)
    }

    override fun makeDst(w: Int, h: Int): Bitmap {
        return makeBitmap(w, h, R.drawable.ic_destination)
    }


    override fun getDescription(): String {
        return "4 不透明"
    }
}


class CustomXfermodeView5 : CustomXfermodeView2 {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mCommentPaint = Paint()

    init {
        mCommentPaint.textSize = TEXT_SIZE
    }

    override fun drawContent(canvas: Canvas, x: Float, y: Float, mode: PorterDuff.Mode) {
        val comment = getComment(mode) + getEquationOld(mode)
        val lines = comment.split('\n')
        for (i in lines.indices) {
            canvas.drawText(lines[i], x, y + mCommentPaint.textSize * (i + 1), mCommentPaint)
        }
    }

    /**
     * 注释
     */
    private fun getComment(mode: PorterDuff.Mode): String {
        var comment = mode.ordinal.toString() + " " + mode.name + "\n"
        comment += when (mode) {
            PorterDuff.Mode.CLEAR -> "清除"
            PorterDuff.Mode.SRC -> "仅 SRC"
            PorterDuff.Mode.DST -> "仅 DST"
            PorterDuff.Mode.SRC_OVER -> "SRC 在上\n 4 中直接覆盖"
            PorterDuff.Mode.DST_OVER -> "DST 在上"
            PorterDuff.Mode.SRC_IN -> "SRC 相交部分\n这个 in 是 in DST 的意思\n即 SRC 在 DST 内的部分"
            PorterDuff.Mode.DST_IN -> "DST 相交部分"
            PorterDuff.Mode.SRC_OUT -> "SRC 不相交部分"
            PorterDuff.Mode.DST_OUT -> "DST 不相交部分"
            PorterDuff.Mode.SRC_ATOP -> "SRC 相交部分在 DST 上"
            PorterDuff.Mode.DST_ATOP -> "DST 相交部分在 SRC 上"
            PorterDuff.Mode.XOR -> "去除相交部分"
            PorterDuff.Mode.DARKEN -> "相交部分加深"
            PorterDuff.Mode.LIGHTEN -> "相交部分变浅"
            PorterDuff.Mode.MULTIPLY -> "相交部分相乘"
            PorterDuff.Mode.SCREEN -> ""
            PorterDuff.Mode.ADD -> "相加，在 01 之间"
            PorterDuff.Mode.OVERLAY -> ""
            else -> ""
        }
        return comment
    }

    /**
     * 计算公式
     * 新版 API 形式
     */
    private fun getEquation(mode: PorterDuff.Mode): String {
        var result = "\n";
        result += when (mode) {
            PorterDuff.Mode.CLEAR -> "αout=0\nCout=0"
            PorterDuff.Mode.SRC -> "αout=αsrc\nCout=Csrc"
            PorterDuff.Mode.DST -> "αout=αdst\nCout=Cdst"
            PorterDuff.Mode.SRC_OVER -> "αout=αsrc+(1−αsrc)∗αdst\nCout=Csrc+(1−αsrc)∗Cdst"
            PorterDuff.Mode.DST_OVER -> "αout=αdst+(1−αdst)∗αsrc\nCout=Cdst+(1−αdst)∗Csrc"
            PorterDuff.Mode.SRC_IN -> "αout=αsrc∗αdst\nCout=Csrc∗αdst"
            PorterDuff.Mode.DST_IN -> "αout=αsrc∗αdst\nCout=Cdst∗αsrc"
            PorterDuff.Mode.SRC_OUT -> "αout=(1−αdst)∗αsrc\nCout=(1−αdst)∗Csrc"
            PorterDuff.Mode.DST_OUT -> "αout=(1−αsrc)∗αdst\nCout=(1−αsrc)∗Cdst"
            PorterDuff.Mode.SRC_ATOP -> "αout=αdst\nCout=αdst∗Csrc+(1−αsrc)∗Cdst"
            PorterDuff.Mode.DST_ATOP -> "αout=αsrc\nCout=αsrc∗Cdst+(1−αdst)∗Csrc"
            PorterDuff.Mode.XOR -> "αout=(1−αdst)∗αsrc+(1−αsrc)∗αdst\nCout=(1−αdst)∗Csrc+(1−αsrc)∗Cdst"
            PorterDuff.Mode.DARKEN -> "αout=αsrc+αdst−αsrc∗αdst\nCout=(1−αdst)∗Csrc+(1−αsrc)∗Cdst+min(Csrc,Cdst)"
            PorterDuff.Mode.LIGHTEN -> "αout=αsrc+αdst−αsrc∗αdst\nCout=(1−αdst)∗Csrc+(1−αsrc)∗Cdst+max(Csrc,Cdst)"
            PorterDuff.Mode.MULTIPLY -> "αout=αsrc∗αdst\nCout=Csrc∗Cdst"
            PorterDuff.Mode.SCREEN -> "αout=αsrc+αdst−αsrc∗αdst\nCout=Csrc+Cdst−Csrc∗Cdst"
            PorterDuff.Mode.ADD -> "αout=max(0,min(αsrc+αdst,1))\nCout=max(0,min(Csrc+Cdst,1))"
        //对公式是否正确有疑问
            PorterDuff.Mode.OVERLAY -> "αout=αsrc+αdst−αsrc∗αdst\n?? Cout=2∗Cdst<αdst ? 2∗Csrc∗Cdst : αsrc∗αdst−2(αdst−Csrc)(αsrc−Cdst)"
            else -> ""
        }
        return result
    }

    /**
     * 计算公式
     * 旧版 API 形式
     */
    private fun getEquationOld(mode: PorterDuff.Mode): String {
        var result = "\n";
        result += when (mode) {
            PorterDuff.Mode.CLEAR -> "[0,\n 0]"
            PorterDuff.Mode.SRC -> "[Sa,\n Sc]"
            PorterDuff.Mode.DST -> "[Da,\n Dc]"
            PorterDuff.Mode.SRC_OVER -> "[Sa + (1 - Sa)*Da,\n Rc = Sc + (1 - Sa)*Dc]"
            PorterDuff.Mode.DST_OVER -> "[Sa + (1 - Sa)*Da,\n Rc = Dc + (1 - Da)*Sc]"
            PorterDuff.Mode.SRC_IN -> "[Sa * Da,\n Sc * Da]"
            PorterDuff.Mode.DST_IN -> "[Sa * Da,\n Sa * Dc]"
            PorterDuff.Mode.SRC_OUT -> "[Sa * (1 - Da),\n Sc * (1 - Da)]"
            PorterDuff.Mode.DST_OUT -> "[Da * (1 - Sa),\n Dc * (1 - Sa)]"
            PorterDuff.Mode.SRC_ATOP -> "[Da,\n Sc * Da + (1 - Sa) * Dc]"
            PorterDuff.Mode.DST_ATOP -> "[Sa,\n Sa * Dc + Sc * (1 - Da)]"
            PorterDuff.Mode.XOR -> "[Sa + Da - 2 * Sa * Da,\n Sc * (1 - Da) + (1 - Sa) * Dc]"
            PorterDuff.Mode.DARKEN -> "[Sa + Da - Sa*Da,\n Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)]"
            PorterDuff.Mode.LIGHTEN -> "[Sa + Da - Sa*Da,\n Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]"
            PorterDuff.Mode.MULTIPLY -> "[Sa * Da,\n Sc * Dc]"
            PorterDuff.Mode.SCREEN -> "[Sa + Da - Sa * Da,\n Sc + Dc - Sc * Dc]"
            PorterDuff.Mode.ADD -> "Saturate(S + D)\n[max(0,min(Sa + Da,1)),\n max(0,min(Sc + Dc,1))]"
        //对公式是否正确有疑问
            PorterDuff.Mode.OVERLAY -> "[Sa + Da - Sa * Da,\n?? 2 * Dc ≤ Da ? 2 * Sc * Dc : Sa * Da - 2 * (Da - Dc) * (Sa - Sc))"
            else -> ""
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //简单扩大宽度
        setMeasuredDimension(measuredWidth * 3, measuredHeight)
    }

    override fun drawBackground(): Boolean {
        return false
    }

    override fun getDescription(): String {
        return "注释"
    }
}