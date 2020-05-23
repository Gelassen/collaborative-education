package ru.home.collaborativeeducation.network

import io.reactivex.Observable
import ru.home.collaborativeeducation.model.*
import ru.home.collaborativeeducation.model.converter.CourseWithMetaConverter
import ru.home.collaborativeeducation.model.database.CourseSourceEntity
import ru.home.collaborativeeducation.storage.model.CourseWithMetadataAndCommentsEntity
import ru.home.collaborativeeducation.storage.model.ItemMetadataEntity
import ru.home.collaborativeeducation.storage.model.LikesEntity

class NetworkRepository(val api: Api) {

    fun getCategoryViewItems(): Observable<List<CategoryViewItem>> {
        return api.getAllCategories
            .flatMap { it ->
                val result = mutableListOf<CategoryViewItem>()
                for (item in it.status.payload) {
                    val obj = CategoryViewItem(item.uid, item.title)
                    result.add(obj)
                }
                Observable.just(result)
            }
    }

    fun saveCategoryViewItem(data: CategoryViewItem): Observable<CategoryViewItem> {
        return api.create(data)
            .flatMap {  it ->
                val item = it.status.payload[0]
                val response = CategoryViewItem(item.uid, item.title)
                Observable.just(response)
            }
    }

    fun getCoursesForCategory(categoryUid: String): Observable<List<CourseViewItem>> {
        return api.getAllCoursesForCategory(categoryUid)
            .flatMap { it ->
                val result = mutableListOf<CourseViewItem>()
                for (item in it.status.payload) {
                    val obj = CourseViewItem(item.uid, item.title, item.categoryUid)
                    result.add(obj)
                }
                Observable.just(result)
            }
    }

    fun saveCourseViewItem(data: CourseViewItem): Observable<CourseViewItem> {
        return api.create(data)
            .flatMap {  it ->
                val item = it.status.payload[0]
                val response = CourseViewItem(item.uid, item.title, item.categoryUid)
                Observable.just(response)
            }
    }

    fun getSourcesForCourse(categoryUid: String, courseUid: String): Observable<List<CourseWithMetadataAndComments>> {
        return api.getAllSourcesForCourse(categoryUid, courseUid)
            .flatMap { it ->
                val result = mutableListOf<CourseWithMetadataAndCommentsEntity>()
                for (item in it.status.payload) {
                    val obj = CourseWithMetadataAndCommentsEntity(
                        CourseSourceEntity(item.uid, item.title, item.source, item.courseUid, item.users),
                        ItemMetadataEntity(LikesEntity()) // TODO add likes support and process them (replace stub)
                    )
                    result.add(obj)
                }
                Observable.just(result)
            }
            .flatMap { it ->
                Observable.just(CourseWithMetaConverter().convert(it))
            }
    }

    fun getSourcesWithMetaForCourse(categoryUid: String, courseUid: String): Observable<List<CourseWithMetadataAndComments>> {
        return api.getAllSourcesWithMetaForCourse(categoryUid, courseUid)
            .flatMap { it ->
                for (item in it.status.payload) {
                    item.metadata.likes.courseUid = item.source.uid
                }
                Observable.just(it)
            }
            .flatMap { it ->
                Observable.just(it.status.payload)
            }
    }

    fun saveSourceForCourse(source: CourseSourceItem): Observable<CourseWithMetadataAndComments> {
        return api.create(source)
            .flatMap { it ->
                val item = it.status.payload[0]
//                val obj = CourseWithMetadataAndCommentsEntity(
//                    CourseSourceEntity(item.uid, item.title, item.source, item.courseUid, item.users),
//                    ItemMetadataEntity(LikesEntity(-1, 0 , item.uid)) // TODO add likes support and process them (replace stub)
//                )
                Observable.just(item)
            }
//            .flatMap { it ->
//                Observable.just(CourseWithMetaConverter().convert(it))
//            }
    }

    fun saveLike(item: CourseWithMetadataAndComments): Observable<CourseWithMetadataAndComments> {
        return api.create(item)
            .flatMap { it ->
                if (it.code == 200) {
                    val item = it.status.payload[0]
                    Observable.just(
                        CourseWithMetadataAndComments(
                            item.source,
                            item.metadata
                        )
                    )
                } else {
                    Observable.just(
                        CourseWithMetadataAndComments(
                            CourseSourceItem(),
                            ItemMetadata(Likes(), arrayListOf())
                        )
                    )
                }
            }
    }

}