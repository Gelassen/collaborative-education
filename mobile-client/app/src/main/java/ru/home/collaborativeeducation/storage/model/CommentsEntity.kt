package ru.home.collaborativeeducation.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentsEntity(
    @PrimaryKey(autoGenerate = true) val commentUid: Long?,
    @ColumnInfo(name = "topic_uid") val topicUid: Long,
    @ColumnInfo(name = "comment") val comment: String
)