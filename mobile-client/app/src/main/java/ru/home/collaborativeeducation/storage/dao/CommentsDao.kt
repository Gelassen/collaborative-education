package ru.home.collaborativeeducation.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.home.collaborativeeducation.storage.model.CommentsEntity

@Dao
interface CommentsDao {

    @Query("SELECT * FROM CommentsEntity WHERE topic_uid = :topicUid")
    fun getAll(topicUid: Long): List<CommentsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<CommentsEntity>): List<Long>
}


