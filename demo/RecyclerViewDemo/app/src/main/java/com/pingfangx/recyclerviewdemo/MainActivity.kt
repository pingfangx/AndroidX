package com.pingfangx.recyclerviewdemo

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val data: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    public fun onClickBtnRemove(view: View) {
        data.removeAt(0)
        recycler_view.adapter?.notifyItemRemoved(0)
    }
    public fun onClickBtnAdd(view: View) {
        data.add(0, "a")
        recycler_view.adapter?.notifyItemInserted(0)
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(this)

        for (i in 1..100) {
            data.add(i.toString())
        }

        val adapter = StringAdapter(this, data)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = layoutManager
    }

    class StringAdapter(private val context: Context, private val data: List<String>) : RecyclerView.Adapter<StringViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
            return StringViewHolder(viewType, MyTextView(context))
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
            holder.bindTo(data[position])
        }

        override fun getItemViewType(position: Int): Int {
            return position % 1;
        }
    }

    class StringViewHolder(private val viewType: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView as TextView
        fun bindTo(data: String) {
            textView.text = "$data,type=$viewType"
        }
    }

    class MyTextView : TextView {

        constructor(ctx: Context) : super(ctx) {}

        constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
        }
    }
}
