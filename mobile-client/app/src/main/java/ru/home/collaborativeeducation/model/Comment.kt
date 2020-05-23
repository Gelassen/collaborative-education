package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable

data class Comment(
    val commentUid: Long?,
    val topicUid: Long,
    val comment: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readLong(),
        parcel.readString()!!
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(commentUid)
        parcel.writeLong(topicUid)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}