package ru.home.collaborativeeducation.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.home.collaborativeeducation.model.database.CategoryViewItemEntity

@Dao
interface CategoryViewItemDao {

    @Query("SELECT * FROM CategoryViewItemEntity")
    fun getAll(): List<CategoryViewItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<CategoryViewItemEntity>): List<Long>
}