package ru.home.collaborativeeducation.ui.course.details

import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CourseWithMetadataAndComments
import ru.home.collaborativeeducation.model.Likes
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.storage.Cache
import javax.inject.Inject

class CourseDetailsViewModel: ViewModel() {

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

    private val data: MutableLiveData<DataWrapper> = MutableLiveData()

    private lateinit var cache: Cache

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        cache = Cache(application)
    }

    fun getModel(): LiveData<DataWrapper> {
        return data
    }

    fun onStart(categoryUid: Long, courseUid: Long) {
        service.getSourcesWithMetaForCourse(categoryUid.toString(), courseUid.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                this.data.postValue(DataWrapper(DataWrapper.TYPE_DATA, it))
            }
    }

    fun onLike(item: CourseWithMetadataAndComments) {
        service.saveLike(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
//                this.data.postValue(it)
                // TODO distinguish change callback on single item and a whole dataset
                this.data.postValue(DataWrapper(DataWrapper.TYPE_UPDATE, it))
            }
    }

    class DataWrapper {

        companion object {
            val TYPE_DATA = 1
            val TYPE_UPDATE = 0
        }

        val type: Int
        val data: List<CourseWithMetadataAndComments>

        constructor(type: Int, data: CourseWithMetadataAndComments) {
            this.type = type
            this.data = arrayListOf()
            this.data.add(data)
        }

        constructor(type: Int, data: List<CourseWithMetadataAndComments>) {
            this.type = type
            this.data = data
        }
    }
}