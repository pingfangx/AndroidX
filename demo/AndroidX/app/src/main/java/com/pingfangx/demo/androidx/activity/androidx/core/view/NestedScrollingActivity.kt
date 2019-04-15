package com.pingfangx.demo.androidx.activity.androidx.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ViewCompat
import com.pingfangx.demo.androidx.R
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import kotlinx.android.synthetic.main.activity_nested_scrolling.*


/**
 * 嵌套滚动
 *
 * @author pingfangx
 * @date 2019/4/15
 */
class NestedScrollingActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        val data = mutableListOf<String>()
        for (i in 0..100) {
            data.add(i.toString())
        }
        recycler_view.adapter = object : BaseTextAdapter<String>(this, data) {}
    }
}

class NestedScrollingLinearLayout : LinearLayout, NestedScrollingParent {
    private var mTopViewHeight = 0
    private var mRecyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private val mScroller by lazy {
        OverScroller(context)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        mRecyclerView = findViewById(R.id.recycler_view)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = findViewById<View>(R.id.tv_top).measuredHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // RecyclerView 的高度，需要手动设置，否则仅有初始化时高度
        mRecyclerView?.layoutParams?.height = measuredHeight - findViewById<View>(R.id.tv_sticky).measuredHeight
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        val hiddenTop = dy > 0 && scrollY < mTopViewHeight
        val showTop = dy < 0 && scrollY > 0 && !canScrollVertically(-1)

        if (hiddenTop or showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return if (scrollY >= mTopViewHeight || (scrollY == 0 && velocityY < 0)) {
            // 如果 >= mTopViewHeight 说明顶部已经完全滑出，不再需要处理
            // 如果为 0 表示在顶部，此时如果向下滑，也不需要处理
            false
        } else {
            fling(velocityY.toInt())
            true
        }
    }

    fun fling(velocityY: Int) {
        mScroller.fling(0, scrollY, 0, velocityY, 0, 0, 0, mTopViewHeight)
        invalidate()
    }

    override fun scrollTo(x: Int, y: Int) {
        var dy = y
        if (dy < 0) {
            dy = 0
        }
        if (dy > mTopViewHeight) {
            dy = mTopViewHeight
        }
        if (dy != scrollY) {
            super.scrollTo(x, dy)
        }
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }
    }
}