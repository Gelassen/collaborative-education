package ru.home.collaborativeeducation.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.home.collaborativeeducation.model.database.CourseViewItemEntity

@Dao
interface CourseViewItemDao {

    @Query("SELECT * FROM CourseViewItemEntity WHERE course_uid = :courseId")
    fun getAll(courseId: Long): List<CourseViewItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemTests: List<CourseViewItemEntity>): List<Long>
}