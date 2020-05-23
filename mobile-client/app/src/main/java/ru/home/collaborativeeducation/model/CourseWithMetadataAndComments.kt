package ru.home.collaborativeeducation.model

import android.os.Parcel
import android.os.Parcelable
import ru.home.collaborativeeducation.network.model.Payload

class CourseWithMetadataAndComments : Parcelable, Payload {

    lateinit var source: CourseSourceItem
    lateinit var metadata: ItemMetadata

    constructor()

    constructor(source: CourseSourceItem, metadata: ItemMetadata) {
        this.source = source
        this.metadata = metadata
    }

    constructor(parcel: Parcel) : this() {
        source = parcel.readParcelable(CourseSourceItem::class.java.classLoader)!!
        metadata = parcel.readParcelable(ItemMetadata::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeParcelable(metadata, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseWithMetadataAndComments> {
        override fun createFromParcel(parcel: Parcel): CourseWithMetadataAndComments {
            return CourseWithMetadataAndComments(parcel)
        }

        override fun newArray(size: Int): Array<CourseWithMetadataAndComments?> {
            return arrayOfNulls(size)
        }
    }

}