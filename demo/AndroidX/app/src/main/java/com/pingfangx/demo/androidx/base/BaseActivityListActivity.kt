package com.pingfangx.demo.androidx.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.view.View
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_COMMON
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import com.pingfangx.demo.androidx.common.VirtualActivity

/**
 * Activity 列表
 *
 * @author pingfangx
 * @date 2018/7/2
 */
abstract class BaseActivityListActivity : BaseListActivity() {
    private var mActivityList: MutableList<ActivityItem> = mutableListOf()

    /**
     * 解析所有 list
     */
    private fun generateActivityList(): MutableList<ActivityItem> {
        val activityArray = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities
        val activityList: MutableList<ActivityItem> = mutableListOf()
        // activity 包名前缀，Main 被过滤
        val activityPackageNamePre = "$packageName.activity"
        //处理所有的 Activity
        for (activityInfo in activityArray) {
            if (activityInfo.name.startsWith(activityPackageNamePre)) {
                insertItem(activityList, ActivityItem(activityInfo))
            }
        }
        return activityList
    }

    protected open fun initActivityList() {}

    override fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<*> {
        mActivityList = generateActivityList()
        initActivityList()
        return object : BaseTextAdapter<ActivityItem>(this, mActivityList) {
            override fun getItemText(t: ActivityItem): String = t.activityName
        }.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ActivityItem> {
            override fun onItemClick(view: View, position: Int, t: ActivityItem) {
                if (t.isParent.not()) {
                    //不是 parent，可以直接打开
                    if (t.isVirtual) {
                        //是虚拟的
                        startActivity(Intent(this@BaseActivityListActivity, VirtualActivity::class.java)
                                .putExtra(INTENT_EXTRA_COMMON, t.name))
                    } else {
                        startActivity(Intent().setClassName(t.packageName, t.name))
                    }
                } else {
                    //是 parent，执行切换
                    t.isOpen = !t.isOpen
                    toggle(position, t, t.isOpen)
                }
            }
        })
    }

    /**
     * 切换
     */
    private fun toggle(position: Int, activityItem: ActivityItem, open: Boolean) {
        if (open) {
            //添加
            //依次展开只有一个 child 的结点，直到 child 或不只一个 child
            val allChildren = activityItem.listUntilChildren()
            mActivityList.addAll(position + 1, allChildren)
            mAdapter.notifyItemRangeInserted(position + 1, allChildren.size)
            //展开到第一个 child
            var firstChildIndex = -1
            for ((i, child) in allChildren.withIndex()) {
                if (child.isParent.not()) {
                    firstChildIndex = i
                    break
                }
            }
            if (firstChildIndex == -1) {
                //如果没有 child，说明都是 parent 取到最后一个
                firstChildIndex = allChildren.size - 1
            }
            //将 parent 都标记为展开
            for ((i, child) in allChildren.withIndex()) {
                if (i > firstChildIndex) {
                    break
                }
                if (child.isParent) {
                    val childrenOfChild = child.listAllChildren()
                    if (childrenOfChild.contains(allChildren[firstChildIndex])) {
                        child.isOpen = true
                    }
                }
            }
        } else {
            //移除，因为关闭时可能有多个 child 展示，所以使用 listAllChildren
            val allChildren = activityItem.listAllChildren()
            val size = mActivityList.size
            mActivityList.removeAll(allChildren)
            mAdapter.notifyItemRangeRemoved(position + 1, size - mActivityList.size)
            for (child in allChildren) {
                if (child.isParent) {
                    child.isOpen = false
                }
            }
        }
    }

    protected fun addVirtualActivity(name: String) {
        val activityPackageNamePre = "$packageName.activity."
        val activityName = if (name.startsWith(activityPackageNamePre).not()) {
            activityPackageNamePre + name
        } else {
            name
        }
        insertItem(mActivityList, ActivityItem(activityName, isParent = false, virtual = true))
    }

    /**
     * 插入 item
     *
     * 有 parent，添加
     * 没有 parent，创建，添加
     */
    private fun insertItem(activityList: MutableList<ActivityItem>, activityItem: ActivityItem): ActivityItem {
        val parent = findParent(activityList, activityItem)
                ?: createParent(activityList, activityItem)
        parent.addChild(activityItem)
        return parent
    }

    /**
     * 查找 parent
     *
     */
    private fun findParent(activityList: List<ActivityItem>, activityItem: ActivityItem): ActivityItem? {
        for (item in activityList) {
            if (item.isParent) {
                if (item.name == activityItem.parentName) {
                    return item
                } else if (item.isParent) {
                    //在 children 中查找
                    val result = findParent(item.children, activityItem)
                    if (result != null) {
                        //如果找到则返回，如果没找到，则继续循环
                        return result
                    }
                }
            }
        }
        return null
    }

    /**
     * 创建 parent
     */
    private fun createParent(activityList: MutableList<ActivityItem>, activityItem: ActivityItem): ActivityItem {
        val activityPackageNamePre = "$packageName.activity"
        val parent = ActivityItem(activityItem.parentName, true)
        if (parent.parentName == activityPackageNamePre) {
            //已到达顶层，添加进数组
            activityList.add(parent)
        } else {
            //如果没到达顶层，当前 parent 是创建的，将当前 parent 也插入
            insertItem(activityList, parent)
        }
        return parent
    }

    /**
     * 继承 ActivityInfo 补充其他内容
     */
    inner class ActivityItem : ActivityInfo {
        constructor(activityInfo: ActivityInfo) : super(activityInfo)
        constructor(name: String, isParent: Boolean, virtual: Boolean = false) {
            this.name = name
            this.isParent = isParent
            this.isVirtual = virtual
        }

        private var _activityName = ""
        /**
         * 展示名
         */
        val activityName: String
            get() {
                if (_activityName.isEmpty()) {
                    _activityName = if (labelRes != 0) {
                        //取标签
                        getString(labelRes)
                    } else {
                        //label 为 0 ，尝试取 title
                        getTitleFromRes(name)
                    }
                }
                return " ".repeat(level * 4) + _activityName + if (isParent) "..." else ""
            }

        /**
         * 是否是父类
         */
        var isParent = false
        /**
         * 是否是虚拟的
         */
        var isVirtual = false
        val children: MutableList<ActivityItem> = mutableListOf()

        /**
         * 构造函数中 name 的赋值在后，所以需要 lazy
         */
        val parentName: String by lazy {
            name.split(".").dropLast(1).joinToString(".")
        }

        /**
         * 标记是否打开
         */
        var isOpen = false

        /**
         * 级别，用于缩进
         */
        private var level = 0

        /**
         * 添加
         * 设置 level 并排序
         */
        fun addChild(activityItem: ActivityItem) {
            activityItem.level = level + 1
            children.add(activityItem)
            children.sortWith(Comparator { o1, o2 ->
                if (o1.isParent && o2.isParent.not()) {
                    //parent 小，前者小
                    -1
                } else if (o1.isParent.not() && o2.isParent) {
                    // parent 小，后者小
                    1
                } else {
                    o1.name.compareTo(o2.name)
                }
            })
        }

        /**
         * 列出 children，包括 children
         */
        fun listAllChildren(): MutableList<ActivityItem> {
            val allChildren: MutableList<ActivityItem> = mutableListOf()
            if (isParent) {
                allChildren.addAll(children)
                for (child in children) {
                    if (child.isParent) {
                        allChildren.addAll(child.listAllChildren())
                    }
                }
            }
            return allChildren
        }

        /**
         * 用于展开时列出，类似拼合包的功能，如果包中只有一个 child ，则继承展开
         */
        fun listUntilChildren(): MutableList<ActivityItem> {
            val allChildren: MutableList<ActivityItem> = mutableListOf()
            if (isParent) {
                allChildren.addAll(children)
                if (children.size == 1) {
                    val child = children[0]
                    if (child.isParent) {
                        //是 parent，继续展开
                        allChildren.addAll(child.listUntilChildren())
                    }
                }
            }
            return allChildren
        }
    }
}