package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable
import ru.home.collaborativeeducation.network.model.Payload

class Likes(): Parcelable, Payload {

    var likesUid: Long = 0
    var counter: Long? = 0
    var courseUid: Long?  = 0
    var users: ArrayList<String>? = arrayListOf()

    constructor(
        likesUid: Long,
        counter: Long,
        courseUid: Long,
        users: ArrayList<String>
    ) : this() {
        this.likesUid = likesUid
        this.counter = counter
        this.courseUid = courseUid
        this.users = users
    }

    constructor(parcel: Parcel) : this() {
        likesUid = parcel.readLong()
        counter = parcel.readLong()
        courseUid = parcel.readLong()
        users = arrayListOf<String>()
        parcel.readStringList(users)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(likesUid)
        parcel.writeLong(counter ?: 0 )
        parcel.writeLong(courseUid ?: -1)
        parcel.writeStringList(users)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Likes> {
        override fun createFromParcel(parcel: Parcel): Likes {
            return Likes(parcel)
        }

        override fun newArray(size: Int): Array<Likes?> {
            return arrayOfNulls(size)
        }
    }
}