package ru.home.collaborativeeducation.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.storage.model.CourseWithMetadataAndCommentsEntity

@Dao
interface CourseSourceDao {

    @Query("SELECT * FROM CourseSourceEntity WHERE course_uid = :courseId")
    fun getAll(courseId: Long): LiveData<List<CourseSourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemTests: List<CourseSourceEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM CourseSourceEntity")
    fun getCourseSourcesWithMeta(): List<CourseWithMetadataAndCommentsEntity>

    @Transaction
    @Query("SELECT * FROM CourseSourceEntity WHERE course_uid = :courseId")
    fun getByCourseCourseSourcesWithMeta(courseId: Long): List<CourseWithMetadataAndCommentsEntity>
}