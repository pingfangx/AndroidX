package com.pingfangx.demo.androidx.activity.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.ViewConfigurationCompat
import com.pingfangx.demo.androidx.base.BaseActivity


/**
 * 滚动器
 * [Android Scroller完全解析，关于Scroller你所需知道的一切](https://blog.csdn.net/guolin_blog/article/details/48719871)
 *
 * @author pingfangx
 * @date 2019/4/15
 */
class ScrollerActivity : BaseActivity() {
    fun onClickTvScrollTo(view: View) {
        getParentViewGroup(view)?.scrollTo(-50, -100)
    }

    fun onClickTvScrollBy(view: View) {
        getParentViewGroup(view)?.scrollBy(-50, -100)
    }

    private fun getParentViewGroup(view: View): ViewGroup? {
        val parent = view.parent
        return if (parent is ViewGroup) {
            parent
        } else {
            null
        }
    }
}

class ScrollerTestView : FrameLayout {
    private var mLastX = 0F
    private var mLastY = 0F
    private var mDownX = 0F
    private var mDownY = 0F
    private val mTouchSlop: Int by lazy {
        ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context))
    }
    private val mScroller by lazy {
        OverScroller(context)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.rawX
                mDownY = ev.rawY
                mLastX = ev.rawX
                mLastY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                mLastX = ev.rawX
                mLastY = ev.rawY
                val diff = Math.max(Math.abs(mLastX - mDownX), Math.abs(mLastY - mDownY))
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val scrolledX = mLastX - event.rawX
                mLastX = event.rawX
                val scrolledY = mLastY - event.rawY
                mLastY = event.rawY
                //计算两次差值，调用 scrollBy
                scrollBy(scrolledX.toInt(), scrolledY.toInt())
            }
            MotionEvent.ACTION_UP -> {
                //提起时从当前位置，滚动到负值，即滚动到 0,0
                mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }
}