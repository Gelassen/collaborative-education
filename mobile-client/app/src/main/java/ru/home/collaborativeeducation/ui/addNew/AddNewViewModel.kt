package ru.home.collaborativeeducation.ui.addNew

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.App
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.ui.IModelListener
import javax.inject.Inject

class AddNewViewModel : ViewModel() {

    class Status {
        var payload: Parcelable? = null
        var status: Int? = 0

        constructor(status: Int) {
            this.status = status
        }

        constructor(payload: Parcelable, status: Int) {
            this.payload = payload
            this.status = status
        }

        companion object {
            const val OK = 1
            const val FAILED = 0
        }
    }

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

    private var listener: IModelListener? = null

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val data: MutableLiveData<Status> = MutableLiveData()

    fun init(app: AppApplication, listener: IModelListener) {
        app.getComponent().inject(this)
        this.listener = listener
    }

    fun getModel(): MutableLiveData<Status> {
        return data
    }

    fun onSaveCategory(data: CategoryViewItem) {
        disposable.add(
            service.saveCategoryViewItem(data)
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                            payload -> this.data.postValue(Status(payload, if (payload.uid != -1L)  Status.OK else Status.FAILED))
                    },
                    {
                            it -> Log.e(App.TAG, "Error was thrown", it)
                    }
                )
        )
    }

    fun onSaveCourse(data: CourseViewItem) {
        disposable.add(
            service.saveCourseViewItem(data)
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                            payload -> this.data.postValue(Status(payload, if (payload.uid != -1L)  Status.OK else Status.FAILED))
                    },
                    {
                            it -> Log.e(App.TAG, "Error was thrown", it)
                    }
                )
        )
    }

    fun onSaveSource(data: CourseSourceItem) {
        disposable.add(
            service.saveSourceForCourse(data)
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                            payload -> this.data.postValue(Status(payload, if (payload.source.uid != -1L)  Status.OK else Status.FAILED))
                    },
                    {
                            it -> Log.e(App.TAG, "Error was thrown", it)
                    }
                )

        )
    }

    fun onDestroy() {
        disposable.clear()
    }

}