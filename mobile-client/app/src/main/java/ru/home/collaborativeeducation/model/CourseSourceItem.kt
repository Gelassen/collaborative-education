package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable

class CourseSourceItem() : Parcelable {

    var uid: Long? = -1
    var title: String? = ""
    var source: String? = ""
    var courseUid: Long = 0L
    var author: String = ""

    constructor(uid: Long, title: String?, source: String?,
                courseUid: Long, author: String) : this() {
        this.uid = uid
        this.title = title
        this.source = source
        this.courseUid = courseUid
        this.author = author
    }

    constructor(parcel: Parcel) : this() {
        uid = parcel.readValue(Long::class.java.classLoader) as? Long
        title = parcel.readString()
        source = parcel.readString()
        courseUid = parcel.readLong()
        author = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(uid)
        parcel.writeString(title)
        parcel.writeString(source)
        parcel.writeLong(courseUid)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseSourceItem> {
        override fun createFromParcel(parcel: Parcel): CourseSourceItem {
            return CourseSourceItem(parcel)
        }

        override fun newArray(size: Int): Array<CourseSourceItem?> {
            return arrayOfNulls(size)
        }
    }


}