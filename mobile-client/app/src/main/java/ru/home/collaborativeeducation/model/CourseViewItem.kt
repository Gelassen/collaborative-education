package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable

data class CourseViewItem(val uid: Long?, val title: String?, val categoryUid: Long, val author: String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(uid)
        parcel.writeString(title)
        parcel.writeLong(categoryUid)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseViewItem> {
        override fun createFromParcel(parcel: Parcel): CourseViewItem {
            return CourseViewItem(parcel)
        }

        override fun newArray(size: Int): Array<CourseViewItem?> {
            return arrayOfNulls(size)
        }
    }
}