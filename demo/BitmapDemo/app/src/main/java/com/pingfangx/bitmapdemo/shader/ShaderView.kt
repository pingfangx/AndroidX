package com.pingfangx.bitmapdemo.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.pingfangx.bitmapdemo.R

/**
 * Shader
 *
 * @author pingfangx
 * @date 2018/5/2
 */

abstract class ShaderView : View {
    val colorArray = intArrayOf(Color.RED, Color.GREEN, Color.BLUE)
    val positionArray = floatArrayOf(0F, 0.5F, 0.6F)
    val startColor = Color.RED
    val endColor = Color.BLUE

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val shader = createShader()
        val paint = Paint()
        paint.shader = shader
        val rect = Rect(0, 0, width, height)
        canvas?.drawRect(rect, paint)
        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 40F;
        var text = javaClass.simpleName.replace("View", "")
        text = text.replace("Tile", "")
        canvas?.drawText(text, 0F, textPaint.textSize, textPaint)
    }

    abstract fun createShader(): Shader;
}

open class BitmapShaderView : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_pingfangx)
        return createShader(bitmap)
    }

    open fun createShader(bitmap: Bitmap): Shader {
        return BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
}

class LinearGradient1View : ShaderView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        //0-50，即向右下角的红蓝渐变
        return LinearGradient(0F, 0F, 50F, 50F, startColor, endColor, Shader.TileMode.REPEAT)
    }
}

class LinearGradient2View : ShaderView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        /*
        2 个颜色似乎不空易看出效果，其结果是
        到 0.5 变为绿色，0.6 变化蓝色，然后中间区域就渐变
         */
        return LinearGradient(0F, 0F, width.toFloat(), 0F, colorArray, positionArray, Shader.TileMode.REPEAT)
    }
}

class RadialGradient1View : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        return RadialGradient(50F, 50F, 100F, colorArray, positionArray, Shader.TileMode.REPEAT)
    }
}

class RadialGradient2View : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        return RadialGradient(50F, 50F, 100F, startColor, endColor, Shader.TileMode.REPEAT)
    }
}

class SweepGradient1View : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        return SweepGradient(100F, 100F, colorArray, positionArray)
    }
}

class SweepGradient2View : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {
        return SweepGradient(100F, 100F, startColor, endColor)
    }
}

class ComposeShaderView : ShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(): Shader {

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_pingfangx)
        val shaderA = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        val shaderB = RadialGradient(50F, 50F, 100F, Color.RED, Color.BLUE, Shader.TileMode.REPEAT)
        return ComposeShader(shaderA, shaderB, PorterDuff.Mode.ADD)
    }
}

class ClampBitmapShaderView : BitmapShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(bitmap: Bitmap): Shader {
        return BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
}


class MirrorBitmapShaderView : BitmapShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(bitmap: Bitmap): Shader {
        return BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR)
    }
}

class RepeatBitmapShaderView : BitmapShaderView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createShader(bitmap: Bitmap): Shader {
        return BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }
}