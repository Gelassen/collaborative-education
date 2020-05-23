package ru.home.collaborativeeducation.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import io.reactivex.Single
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.database.CourseViewItemEntity
import ru.home.collaborativeeducation.model.database.CategoryViewItemEntity
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.storage.AppDatabase
import ru.home.collaborativeeducation.storage.model.*

class InternalStorageRepository(context: Context) {

    var db: AppDatabase

    init {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-app"
        ).build()
    }

    /* START BLOCK CategoryViewItem */

    fun saveCategoryViewItem(data: CategoryViewItem): Single<List<Long>> {
        return saveCategoryViewItem(listOf(data))
    }

    fun saveCategoryViewItem(data: List<CategoryViewItem>): Single<List<Long>> {
        val localData = convertToLocal(data)
        return Single.fromCallable {
            db.categoryViewItemDao.insert(localData)
        }
    }

    fun getCategoryViewItems(): Single<List<CategoryViewItemEntity>> {
        return Single.fromCallable {
            db.categoryViewItemDao.getAll()
        }
    }

    /* END BLOCK CategoryViewItem */

    /* START BLOCK CourseViewItem */

    fun saveCourseViewItem(data: CourseViewItem): Single<List<Long>> {
        return saveCourseViewItem(listOf(data))
    }

    fun saveCourseViewItem(data: List<CourseViewItem>): Single<List<Long>> {
        val localData = convertToLocalCourse(data)
        return Single.fromCallable {
            db.courseViewItemDao.insert(localData)
        }
    }

    fun getCourseViewItem(categoryUid: Long): Single<List<CourseViewItemEntity>> {
        return Single.fromCallable {
            db.courseViewItemDao.getAll(categoryUid)
        }
    }

    /* END BLOCK CourseViewItem */

    /* START BLOCK CourseSourceEntity */

    fun getCourseSource(uid: Long): LiveData<List<CourseSourceEntity>> {
        return db.courseSourceDao.getAll(uid)
    }

    fun saveCourseSourceItem(data: CourseSourceItem): Single<List<Long>> {
        return saveCourseSourceItem(listOf(data))
    }

    fun saveCourseSourceItem(data: List<CourseSourceItem>): Single<List<Long>> {
        val localData = convertToLocalSource(data)
        return Single.fromCallable {
            db.courseSourceDao.insert(localData)
        }
    }

    fun getAllCourseSourcesWithMeta(): Single<List<CourseWithMetadataAndCommentsEntity>> {
        return Single.fromCallable {
            db.courseSourceDao.getCourseSourcesWithMeta()
        }
    }

    fun getByCategoryCourseSourcesWithMeta(uid: Long): Single<List<CourseWithMetadataAndCommentsEntity>> {
        return Single.fromCallable {
            db.courseSourceDao.getByCourseCourseSourcesWithMeta(uid)
        }
    }

    /* END BLOCK CourseSourceEntity */

    /* START BLOCK MetadataEntity */

    fun saveLikes(item: LikesEntity): Single<Long> {
        return Single.fromCallable {
            db.likesDao.insert(item)
        }
    }

    fun getLikes(): Single<List<TestItemData>>? {
        return Single.fromCallable {
            db.likesDao.query()
        }
    }

    fun getCrossRef(): Single<List<LikesAndCommentsCrossRef>> {
        return Single.fromCallable {
            db.likesDao.getAllCrossRefs()
        }
    }

    /* END BLOCK MetadataEntity */

    /* START BLOCK CommentsEntity */

    fun getAllComments(topicUid: Long): Single<List<CommentsEntity>> {
        return Single.fromCallable {
            db.commentsDao.getAll(topicUid)
        }
    }

    fun saveComments(result: List<CommentsEntity>): Single<List<Long>> {
        return Single.fromCallable {
            db.commentsDao.insert(result)
        }
    }

    fun saveLikesAndCommentsCrossRef(item: LikesAndCommentsCrossRef): Single<Long>? {
        return Single.fromCallable {
            db.likesDao.insertLikesAndCommentsCrossRef(item)
        }
    }

    /* END BLOCK CommentsEntity */


    fun clean(): Single<Boolean> {
        return Single.fromCallable {
            db.clearAllTables()
            true
        }
    }

    private fun convertToLocal(data: List<CategoryViewItem>): List<CategoryViewItemEntity> {
        val result = arrayListOf<CategoryViewItemEntity>()
        for (item in data) {
            result.add(CategoryViewItemEntity(0, item.title!!))
        }
        return result
    }

    private fun convertToLocalCourse(data: List<CourseViewItem>): List<CourseViewItemEntity> {
        val result = arrayListOf<CourseViewItemEntity>()
        for (item in data) {
            result.add(CourseViewItemEntity(0, item.title!!, item.categoryUid))
        }
        return result
    }

    private fun convertToLocalSource(data: List<CourseSourceItem>): List<CourseSourceEntity> {
        val result = arrayListOf<CourseSourceEntity>()
        for (item in data) {
            result.add(CourseSourceEntity(if (item.uid!! == -1L) null else item.uid!!, item.title!!, item.source!!, item.courseUid, item.users))
        }
        return result
    }


}