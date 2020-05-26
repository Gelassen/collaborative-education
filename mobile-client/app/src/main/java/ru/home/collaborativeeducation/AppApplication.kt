package ru.home.collaborativeeducation

import android.app.Application
import android.util.Log
import com.splunk.mint.Mint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.di.AppComponent
import ru.home.collaborativeeducation.di.DaggerAppComponent
import ru.home.collaborativeeducation.di.Module
import ru.home.collaborativeeducation.di.NetworkModule
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.storage.Cache
import ru.home.collaborativeeducation.storage.model.CommentsEntity
import ru.home.collaborativeeducation.storage.model.LikesAndCommentsCrossRef
import ru.home.collaborativeeducation.storage.model.LikesEntity
import java.lang.RuntimeException
import kotlin.collections.ArrayList

class AppApplication : Application() {

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = initDagger()

        // Set the application environment
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(this, "29b27c81")

        val cache = Cache(this)
        if (cache.getUuid().equals("")) {
            cache.saveUuid()
        }

        val repo = InternalStorageRepository(this)

        var courseSourceId: Long? = null
        var commentsMember: List<CommentsEntity>? = null

        // seed test data

        repo.clean()
            .flatMap { data ->
                repo.saveCategoryViewItem(getCategoryData())
            }
//        repo.saveCategoryViewItem(getCategoryData())
            .flatMap { categories ->
                repo.saveCourseViewItem(getCourseData(categories))
            }
            .flatMap { courses ->
                repo.saveCourseSourceItem(getCourseSource(courses))
            }
            .flatMap { sources ->
                courseSourceId = sources.get(0)
                repo.saveComments(getComments(courseSourceId!!))
            }
            .flatMap { comments ->
                repo.saveLikes(getLikes(courseSourceId!!))
            }
            .flatMap { likes ->
                repo.getAllComments(courseSourceId!!)
            }
            .flatMap { comments ->
                Log.d(App.TAG, "Comments pack")
                commentsMember = comments
                for (comment in comments) {
                    Log.d(App.TAG, "Comment: " + comment.comment)
                }
                repo.getLikes()
            }
            .flatMap { it ->
                val crossRefItem = LikesAndCommentsCrossRef()
                crossRefItem.likesUid = it.get(0).likes.likesUid
                crossRefItem.commentUid = commentsMember!!.get(0).commentUid
                repo.saveLikesAndCommentsCrossRef(crossRefItem)
            }
            .flatMap { it ->
                repo.getCrossRef()
            }
            .flatMap { it ->
                repo.getLikes()
            }
            .flatMap {
                repo.getAllCourseSourcesWithMeta()
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                Log.d(App.TAG, "Save course item: " + data)
            }

        // TODO extend course with comments to support many to many to many mapping

    }

    private fun getLikes(courseSourceId: Long): LikesEntity {
        val likes = arrayListOf<String>()
        likes.add("<another user>")
        likes.add(Cache(this).getUuid())
        return LikesEntity(0, 1, courseSourceId, likes)
    }

    private fun getComments(courseSourceEntity: Long): List<CommentsEntity> {
        val comments = ArrayList<CommentsEntity>()
        val commentsEntity = CommentsEntity(null, courseSourceEntity, "This is great course!")
        comments.add(commentsEntity)
        return comments
    }

    private fun getCourseSource(courses: List<Long>): List<CourseSourceItem> {
        val result = mutableListOf<CourseSourceItem>()
        result.add(CourseSourceItem(
            -1, "Course source title",
            "Link on video", courses.get(0),
            arrayListOf<String>(Cache(this).getUuid())
        ))
        result.add(CourseSourceItem(
            -1, "Alternative source title",
            "Alternative Link on video",
            courses.get(0),
            arrayListOf<String>("<Alternative author>")
        ))
        return result
    }

    fun getCategoryData(): MutableList<CategoryViewItem> {
        val list = mutableListOf<CategoryViewItem>()
        list.add(CategoryViewItem(1, "First"))
        list.add(CategoryViewItem(2, "second"))
        list.add(CategoryViewItem(3, "Third"))
        return list
    }

    fun getCourseData(categories: List<Long>): MutableList<CourseViewItem> {
        val list = mutableListOf<CourseViewItem>()
        list.add(CourseViewItem(1, "First: Course 1", categories.get(0)))
        list.add(CourseViewItem(2, "First: Course 2", categories.get(0)))
        list.add(CourseViewItem(3, "Second: Course 1", categories.get(2)))
        return list
    }

    fun getComponent(): AppComponent {
        return component!!
    }

    private fun initDagger(): AppComponent {
        return DaggerAppComponent
            .builder()
            .networkModule(NetworkModule(this, getString(R.string.url)))
            .module(Module(this))
            .build()
    }
}