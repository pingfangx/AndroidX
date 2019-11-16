package com.pingfangx.demo.androidx.activity.androidx.recyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pingfangx.demo.androidx.base.ActivityInitializer
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewHolder
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextViewHolder
import com.pingfangx.demo.androidx.base.xxlog

/**
 * 学习缓制
 *
 * @author pingfangx
 * @date 2019/11/16
 */
class RecyclerViewCacheDemo : ActivityInitializer {
    override fun initActivity(activity: BaseActivity) {
        super.initActivity(activity)

        val context = activity
        val frameLayout = FrameLayout(context)
        context.setContentView(frameLayout)

        val recyclerView = DemoRecyclerView(context)
        frameLayout.addView(recyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.RIGHT
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.RIGHT or Gravity.TOP
        frameLayout.addView(linearLayout, layoutParams)

        val tvMessage = TextView(context)
        tvMessage.gravity = Gravity.RIGHT
        recyclerView.mTvMessage = tvMessage

        addBtn(linearLayout, "notifyDataSetChanged", View.OnClickListener { recyclerView.adapter?.notifyDataSetChanged() })
        addBtn(linearLayout, "notifyItemRemoved", View.OnClickListener { recyclerView.adapter?.notifyItemRemoved(1) })
        linearLayout.addView(tvMessage, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = mutableListOf<String>()
        for (i in 0..100) {
            data.add(i.toString())
        }
        recyclerView.adapter = DemoAdapter(context, data)
    }

    fun addBtn(viewGroup: ViewGroup, text: String, onClickListener: View.OnClickListener) {
        val btn = Button(viewGroup.context)
        btn.isAllCaps = false
        btn.text = text
        btn.setOnClickListener(onClickListener)
        viewGroup.addView(btn, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }
}

/**
 * 参考 https://github.com/jokermonn/RecyclerViewVisualization
 */
class DemoRecyclerView : RecyclerView {
    var mTvMessage: TextView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val mRecycler = getValue(this, "mRecycler", RecyclerView::class.java)
        if (mRecycler == null) {
            return
        }
        val mAttachedScrapField = mRecycler::class.java.getDeclaredField("mAttachedScrap")
        mAttachedScrapField.isAccessible = true
        mAttachedScrapField.set(mRecycler, LogArrayList<ViewHolder>("mAttachedScrap"))

        val mCachedViewsField = mRecycler::class.java.getDeclaredField("mCachedViews")
        mCachedViewsField.isAccessible = true
        mCachedViewsField.set(mRecycler, LogArrayList<ViewHolder>("mCachedViews"))

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                showMessage()
            }
        })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        showMessage()
    }

    private fun showMessage() {
        val tvMessage = mTvMessage
        if (tvMessage == null) {
            return
        }
        val mRecycler = getValue(this, "mRecycler", RecyclerView::class.java)
        if (mRecycler == null) {
            return
        }

        val mAttachedScrap = getValue(mRecycler, "mAttachedScrap") as ArrayList<*>?
        //val mChangedScrap = getValue(mRecycler, "mChangedScrap") as ArrayList<*>?
        val mCachedViews = getValue(mRecycler, "mCachedViews") as ArrayList<*>?
        val mViewCacheMax = getValue(mRecycler, "mViewCacheMax") as Int?
        val mRecyclerPool = getValue(mRecycler, "mRecyclerPool")

        val msg = StringBuilder()
        msg.append("childCount=$childCount")
        val layoutManager = layoutManager
        if (layoutManager is LinearLayoutManager) {
            msg.append("\nitem ${layoutManager.findFirstVisibleItemPosition()}-${layoutManager.findLastVisibleItemPosition()}")
        }
        msg.append("\nmAttachedScrap : ${mAttachedScrap?.size}")
        msg.append("\nmCachedViews:${mCachedViews?.size} (max=$mViewCacheMax)")
        addViewHolderInList(msg, mCachedViews)
        addRecyclerPoolInfo(msg, mRecyclerPool)


        tvMessage.text = msg
        msg.insert(0, " \n").toString().xxlog()
    }

    private fun getValue(obj: Any, fieldName: String, clazz: Class<out Any> = obj::class.java): Any? {
        val field = clazz.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(obj)
    }

    private fun addRecyclerPoolInfo(msg: StringBuilder, pool: Any?) {
        if (pool == null) {
            return
        }
        val mScrap = getValue(pool, "mScrap") as SparseArray<*>?
        if (mScrap == null) {
            return
        }
        val mScrapHeapField = Class.forName("androidx.recyclerview.widget.RecyclerView\$RecycledViewPool\$ScrapData").getDeclaredField("mScrapHeap")
        mScrapHeapField.isAccessible = true
        msg.append("\nmRecyclerPool type count=${mScrap.size()}")
        for (i in 0 until mScrap.size()) {
            val key = mScrap.keyAt(i)
            val valude = mScrap.get(key)
            val mScrapHeap = mScrapHeapField.get(valude) as ArrayList<*>
            msg.append("\n    type=$key,cache size= ${mScrapHeap.size}")
            addViewHolderInList(msg, mScrapHeap, "    ")
        }
    }

    private fun addViewHolderInList(msg: StringBuilder, viewHolders: ArrayList<*>?, indent: CharSequence = "") {
        if (viewHolders == null) {
            return
        }
        viewHolders.forEach {
            if (it is ViewHolder) {
                msg.append("\n")
                        .append(indent)
                        .append("postion=" + it.layoutPosition)
                if (it is BaseTextViewHolder<*>) {
                    msg.append(",text=" + it.mTvName.text)
                }
            }
        }
    }
}

class DemoAdapter(mContext: Context, mData: List<String>) : BaseTextAdapter<String>(mContext, mData) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<String> {
        "onCreateViewHolder $viewType".xxlog()
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onCreateViewHolder(itemView: View): BaseRecyclerViewHolder<String> {
        return object : BaseTextViewHolder<String>(itemView) {
            override fun bindTo(t: String, position: Int) {
                //获取之前设置的文字，之前设置的文字也就表示了是复用了哪一个 item
                val old = mTvName.text.toString()
                val lastIndex = old.split("-").last()
                if (lastIndex.isEmpty()) {
                    mTvName.setText(t)
                } else if (lastIndex.equals(t).not()) {
                    mTvName.setText("$lastIndex-$t")
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<String>, position: Int) {
        "onBindViewHolder $position".xxlog()
        super.onBindViewHolder(holder, position)
    }
}

class LogArrayList<E : Any>(val type: String) : ArrayList<E>() {
    override fun add(element: E): Boolean {
        if (element is RecyclerView.ViewHolder) {
            "$type,add : position=${element.adapterPosition}".xxlog()
        }
        return super.add(element)
    }

    override fun add(index: Int, element: E) {
        if (element is RecyclerView.ViewHolder) {
            "$type,addWithIndex : position=${element.adapterPosition}".xxlog()
        }
        super.add(index, element)
    }

    override fun removeAt(index: Int): E {
        val element = get(index)
        if (element is RecyclerView.ViewHolder) {
            "$type,removeAt : position=${element.adapterPosition}".xxlog()
        }
        return super.removeAt(index)
    }

    override fun remove(element: E): Boolean {
        if (element is RecyclerView.ViewHolder) {
            "$type,remove : position=${element.adapterPosition}".xxlog()
        }
        return super.remove(element)
    }
}