package com.pingfangx.demo.androidx.activity.view.textview

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.widget.Button
import com.pingfangx.demo.androidx.base.BaseActivity
import kotlinx.android.synthetic.main.activity_double_click_button.*
import org.jetbrains.anko.toast


/**
 * 双击
 *
 * @author pingfangx
 * @date 2018/9/20
 */
class DoubleClickButtonActivity : BaseActivity() {
    override fun initViews() {
        super.initViews()
        btn_0.setOnClickListener { v -> v.context.toast("单击 0") }
        btn_0.onDoubleClickListener = object : BaseDoubleClickableButton.OnDoubleClickListener {
            override fun onDoubleClick(view: View) {
                view.context.toast("双击 0")
            }
        }

        btn_1.setOnClickListener { v -> v.context.toast("单击 1") }
        btn_1.onDoubleClickListener = object : BaseDoubleClickableButton.OnDoubleClickListener {
            override fun onDoubleClick(view: View) {
                view.context.toast("双击 1")
            }
        }
    }
}

abstract class BaseDoubleClickableButton : Button {
    companion object {
        /**
         * 等待双击的间隔
         */
        val DOUBLE_CLICK_TIMEOUT = ViewConfiguration.getDoubleTapTimeout().toLong()
    }

    var onDoubleClickListener: OnDoubleClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 执行双击
     */
    fun performDoubleClick() {
        if (onDoubleClickListener != null) {
            onDoubleClickListener!!.onDoubleClick(this)
        }
    }

    interface OnDoubleClickListener {
        fun onDoubleClick(view: View)
    }
}

/**
 * 其实本质上两者是一致的，只是一个可以通过 android.os.Handler.hasMessages(int) 判断，一个需要持有
 * 通过延时 post runnable 实现
 * 点击，将等待双击置为 true，在 runnable 中置为 false 并执行单击
 * 如果点击时已经为 true，则移除 callback 并执行双击
 */
class DoubleClickableButtonByPostRunnable : BaseDoubleClickableButton {
    /**
     * 是否等待双击，点击时置为true
     */
    private var mWaitDoubleClick: Boolean = false
    private val mHandler = Handler()
    /**
     * 执行点击的Runnable
     */
    private val mClickRunnable = Runnable {
        mWaitDoubleClick = false
        super@DoubleClickableButtonByPostRunnable.performClick()
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun performClick(): Boolean {
        if (mWaitDoubleClick) {
            mWaitDoubleClick = false
            mHandler.removeCallbacks(mClickRunnable)
            performDoubleClick()
        } else {
            mWaitDoubleClick = true
            mHandler.postDelayed(mClickRunnable, DOUBLE_CLICK_TIMEOUT)
        }
        return false
    }
}

/**
 * 通过发送延时消息实现
 * 该实现与 android.view.GestureDetector.onTouchEvent 中双击的判断一致
 * 点击时判断，如果有消息，则清空消息，并执行双击
 * 否则，发送延时消息
 * 收到延时消息时执行单击
 */
class DoubleClickableButtonBySendMessage : BaseDoubleClickableButton {
    companion object {
        private const val MESSAGE_WHAT_CLICK = 1
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE_WHAT_CLICK -> super@DoubleClickableButtonBySendMessage.performClick()
                else -> {
                }
            }
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun performClick(): Boolean {
        val hasClickMessage = mHandler.hasMessages(MESSAGE_WHAT_CLICK)
        if (hasClickMessage) {
            mHandler.removeMessages(MESSAGE_WHAT_CLICK)
            performDoubleClick()
        } else {
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT_CLICK, DOUBLE_CLICK_TIMEOUT)
        }
        return false
    }

}