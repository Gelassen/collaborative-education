package ru.home.collaborativeeducation.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.home.collaborativeeducation.storage.typeconverter.CollectionToStringConverter

@Entity
@TypeConverters(CollectionToStringConverter::class)
data class CourseSourceEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "course_uid") val courseId: Long,
    @ColumnInfo(name = "users") val users: ArrayList<String>?
)
