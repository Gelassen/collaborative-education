package ru.home.collaborativeeducation.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseViewItemEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "course_uid") val category: Long
)
