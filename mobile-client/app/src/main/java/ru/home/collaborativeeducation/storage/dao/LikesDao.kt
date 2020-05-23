package ru.home.collaborativeeducation.storage.dao

import androidx.room.*
import ru.home.collaborativeeducation.storage.model.LikesAndCommentsCrossRef
import ru.home.collaborativeeducation.storage.model.LikesEntity
import ru.home.collaborativeeducation.storage.model.TestItemData

@Dao
interface LikesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LikesEntity): Long

    @Transaction
    @Query("SELECT * FROM LikesEntity")
    fun query(): List<TestItemData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLikesAndCommentsCrossRef(item: LikesAndCommentsCrossRef): Long

    @Query("SELECT * FROM LikesAndCommentsCrossRef")
    fun getAllCrossRefs(): List<LikesAndCommentsCrossRef>

}