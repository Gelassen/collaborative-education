package ru.home.collaborativeeducation.ui.course.details

import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CourseWithMetadataAndComments
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.storage.Cache
import ru.home.collaborativeeducation.ui.IModelListener
import javax.inject.Inject

class CourseDetailsViewModel: ViewModel() {

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val data: MutableLiveData<DataWrapper> = MutableLiveData()

    private var listener: IModelListener? = null

    private lateinit var cache: Cache

    fun init(application: AppApplication, listener: IModelListener) {
        application.getComponent().inject(this)
        cache = Cache(application)
        this.listener = listener
    }

    fun getModel(): LiveData<DataWrapper> {
        return data
    }

    fun onStart(categoryUid: Long, courseUid: Long) {
        disposable.add(
            service.getSourcesWithMetaForCourse(categoryUid.toString(), courseUid.toString())
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    this.data.postValue(DataWrapper(DataWrapper.TYPE_DATA, it))
                }
        )
    }

    fun onLike(item: CourseWithMetadataAndComments) {
        disposable.add(
            service.saveLike(item)
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    this.data.postValue(DataWrapper(DataWrapper.TYPE_UPDATE, it))
                }
        )
    }

    fun onStop() {
        disposable.clear()
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