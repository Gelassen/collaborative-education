package ru.home.collaborativeeducation.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.home.collaborativeeducation.model.database.CourseViewItemEntity
import ru.home.collaborativeeducation.model.database.CategoryViewItemEntity
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.storage.dao.*
import ru.home.collaborativeeducation.storage.model.CommentsEntity
import ru.home.collaborativeeducation.storage.model.LikesAndCommentsCrossRef
import ru.home.collaborativeeducation.storage.model.LikesEntity

@Database(
    entities = arrayOf(
        CategoryViewItemEntity::class, CourseViewItemEntity::class, CourseSourceEntity::class,
        LikesEntity::class, CommentsEntity::class, LikesAndCommentsCrossRef::class),
    version = 14,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val categoryViewItemDao: CategoryViewItemDao

    abstract val courseViewItemDao: CourseViewItemDao

    abstract val courseSourceDao: CourseSourceDao

    abstract val commentsDao: CommentsDao

    abstract val likesDao: LikesDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database-coledu")
                    .build()
            }
            return INSTANCE!!
        }
    }

}