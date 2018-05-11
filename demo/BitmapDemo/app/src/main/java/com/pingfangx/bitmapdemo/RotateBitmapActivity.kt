package com.pingfangx.bitmapdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView

class RotateBitmapActivity : AppCompatActivity() {
    private var mRotateDegrees = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_bitmap)
    }

    /**
     * 通过 rotation
     */
    fun onClickIvRotate(view: View) {
        val imageView = view as ImageView
        imageView.rotation += ROTATE_DEGREE
    }

    /**
     * 通过 bitmap
     */
    fun onClickIvRotate2(view: View) {
        val imageView = view as ImageView
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val m = Matrix()
            m.postRotate(ROTATE_DEGREE)
            val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
            imageView.setImageBitmap(rotateBitmap)
        }
    }

    /**
     * 通过动画
     */
    fun onClickIvRotate3(view: View) {
        val animation = RotateAnimation(mRotateDegrees, mRotateDegrees + ROTATE_DEGREE, (view.width / 2).toFloat(), (view.height / 2).toFloat())
        mRotateDegrees += ROTATE_DEGREE
        mRotateDegrees = if (mRotateDegrees == 360F) 0F else mRotateDegrees
        animation.duration = 300
        //不需要,只影响 fillBefore ?
        animation.isFillEnabled = true
        //animation.fillAfter = true
        view.startAnimation(animation)
    }

    /**
     * 通过 android.graphics.Canvas.rotate(float, float, float)
     */
    fun onClickRotatableImageView(view: View) {
        val imageView = view as RotatableImageView
        imageView.mRotationDegrees += ROTATE_DEGREE
        imageView.invalidate()
    }

    companion object {
        const val ROTATE_DEGREE = 90F
    }
}

class RotatableImageView : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var mRotationDegrees = 0F
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(mRotationDegrees, (width / 2).toFloat(), (height / 2).toFloat())
        super.onDraw(canvas)
        canvas.restore()
    }
}
