package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable

data class CategoryViewItem(val uid: Long?, val title: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(uid)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun isEmpty() : Boolean {
        return uid == -1L
    }

    companion object CREATOR : Parcelable.Creator<CategoryViewItem> {
        override fun createFromParcel(parcel: Parcel): CategoryViewItem {
            return CategoryViewItem(parcel)
        }

        override fun newArray(size: Int): Array<CategoryViewItem?> {
            return arrayOfNulls(size)
        }
    }
}