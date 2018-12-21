package com.pingfangx.bitmapdemo

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_corner_demo.*


class CornerDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corner_demo)
        initViews()
    }

    private fun initViews() {
        val outerR = floatArrayOf(10f, 20f, 20f, 40f, 10f, 20f, 20f, 40f)
        val innerR = floatArrayOf(10f, 20f, 20f, 40f, 10f, 20f, 20f, 40f)
        val rectF = RectF(10f, 20f, 30f, 40f)
        val roundRectShape = RoundRectShape(outerR, rectF, innerR)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.color = Color.RED
        iv_shape.setBackgroundDrawable(shapeDrawable)
    }
}

/**
 * 基类圆角矩形，在绘制时创建一个新的 roundBitmap
 */
abstract class BaseCornerImageView : ImageView {
    protected val mPaint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        val drawable = drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            bitmap?.let {
                val roundBitmap = createRoundBitmap(this, bitmap, 50)
                mPaint.reset()
                canvas?.drawBitmap(roundBitmap, 0f, 0f, mPaint)
                return
            }
        }
        super.onDraw(canvas)
    }

    abstract fun createRoundBitmap(imageView: ImageView, bitmap: Bitmap, cornerRadius: Int): Bitmap
}

/**
 * 通过 drawRoundRect
 */
class CornerImageViewByRoundRect : BaseCornerImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createRoundBitmap(imageView: ImageView, bitmap: Bitmap, cornerRadius: Int): Bitmap {
        mPaint.reset()
        mPaint.isAntiAlias = true
        //此处的宽高，要注意取imageView还是资源
        //此处的config不能设置为RGB_565
        val output = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)

        //产生一个同样大小的画布
        val canvas = Canvas(output)

        //创建矩形
        val rectF = RectF(0f, 0f, imageView.width.toFloat(), imageView.height.toFloat())

        //绘制圆角矩形
        canvas.drawRoundRect(rectF, cornerRadius.toFloat() * 2, cornerRadius.toFloat(), mPaint)

        //设置SRC_IN
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //绘制图片
        val srcRect = Rect(0, 0, bitmap.width, bitmap.height)
        canvas.drawBitmap(bitmap, srcRect, rectF, mPaint)

        return output
    }
}

/**
 * 通过 drawRoundRect 绘制圆解矩形着色器 shader 取 BitmapShader
 */
class CornerImageViewByBitmapShader : BaseCornerImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createRoundBitmap(imageView: ImageView, bitmap: Bitmap, cornerRadius: Int): Bitmap {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //此处的宽高，要注意取imageView还是资源
        //此处的config不能设置为RGB_565
        val output = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)

        //产生一个同样大小的画布
        val canvas = Canvas(output)

        //创建矩形
        val rectF = RectF(0f, 0f, imageView.width.toFloat(), imageView.height.toFloat())

        //绘制圆解矩形
        canvas.drawRoundRect(rectF, cornerRadius.toFloat() * 2, cornerRadius.toFloat(), mPaint)

        return output
    }
}

/**
 * 通过 drawPath
 */
class ConerImageViewByPath : BaseCornerImageView {
    private val mPath: Path by lazy {
        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        val width = getWidth()
        val height = getHeight()
        path.addRoundRect(RectF(0F, 0F, width.toFloat(), height.toFloat()), 50F, 50F, Path.Direction.CW)
        path
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createRoundBitmap(imageView: ImageView, bitmap: Bitmap, cornerRadius: Int): Bitmap {
        mPaint.reset()
        mPaint.isAntiAlias = true
        //此处的宽高，要注意取imageView还是资源
        //此处的config不能设置为RGB_565
        val output = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)

        //产生一个同样大小的画布
        val canvas = Canvas(output)

        //创建矩形
        val rectF = RectF(0f, 0f, imageView.width.toFloat(), imageView.height.toFloat())

        //绘制 path
        canvas.drawPath(mPath, mPaint)

        //设置SRC_IN
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //绘制图片
        val srcRect = Rect(0, 0, bitmap.width, bitmap.height)
        canvas.drawBitmap(bitmap, srcRect, rectF, mPaint)

        return output
    }
}
