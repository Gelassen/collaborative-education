package ru.home.collaborativeeducation.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.home.collaborativeeducation.storage.typeconverter.CollectionToStringConverter

@Entity
@TypeConverters(CollectionToStringConverter::class)
data class LikesEntity (
    @PrimaryKey(autoGenerate = true) val likesUid: Long = -1,
    @ColumnInfo(name = "counter") val counter: Long = 0,
    @ColumnInfo(name = "course_uid") val courseUid: Long = -1,
    @ColumnInfo(name = "users") val users: ArrayList<String> = arrayListOf()
)