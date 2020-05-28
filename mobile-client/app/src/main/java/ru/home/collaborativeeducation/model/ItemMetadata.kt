package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable

class ItemMetadata() : Parcelable {

    lateinit var likes: Likes
    lateinit var comments: List<Comment>

    constructor(likes: Likes, comments: List<Comment>) : this() {
        this.likes = likes
        this.comments = comments
    }

    constructor(parcel: Parcel) : this() {
        likes = parcel.readParcelable(Likes::class.java.classLoader)!!
        comments = parcel.createTypedArrayList(Comment)!!
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(likes, flags)
        parcel.writeTypedList(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ItemMetadata(likes=${likes.toString()}, comments=$comments)"
    }

    companion object CREATOR : Parcelable.Creator<ItemMetadata> {
        override fun createFromParcel(parcel: Parcel): ItemMetadata {
            return ItemMetadata(parcel)
        }

        override fun newArray(size: Int): Array<ItemMetadata?> {
            return arrayOfNulls(size)
        }
    }
}
