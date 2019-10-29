package com.pingfangx.demo.androidx.activity.android.os

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.pingfangx.demo.androidx.base.ActivityLifecycle
import com.pingfangx.demo.androidx.base.BaseActivity
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_COMMON
import com.pingfangx.demo.androidx.base.extension.addButton
import com.pingfangx.demo.androidx.base.xxlog
import com.pingfangx.demo.androidx.common.VirtualActivity

/**
 *
 * @author pingfangx
 * @date 2019/10/29
 */
class Person(var name: String?, var age: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeInt(age)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person = Person(parcel)

        override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
    }
}

class ParcelableDemo : ActivityLifecycle {
    companion object {
        const val EXTRA_PERSON = "person"
    }

    override fun onCreate(activity: BaseActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        val person: Person? = activity.intent.getParcelableExtra(EXTRA_PERSON)
        if (person == null) {
            "为空".xxlog()
        } else {
            "传递结果 ${person.name},${person.age}".xxlog()
        }
        activity.addButton("传递", View.OnClickListener {
            activity.startActivity(
                    Intent(activity, VirtualActivity::class.java)
                            .putExtra(INTENT_EXTRA_COMMON, ParcelableDemo::class.java.name)
                            .putExtra(EXTRA_PERSON, Person("A", 22))
            )
        })
    }

}