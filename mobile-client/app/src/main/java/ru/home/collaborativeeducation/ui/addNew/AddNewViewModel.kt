package ru.home.collaborativeeducation.ui.addNew

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
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

    private val data: MutableLiveData<Status> = MutableLiveData()

    fun init(app: AppApplication) {
        app.getComponent().inject(this)
    }

    fun getModel(): MutableLiveData<Status> {
        return data
    }

    fun onSaveCategory(data: CategoryViewItem) {
        service.saveCategoryViewItem(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                payload ->
                this.data.postValue(Status(payload, if (payload.uid != -1L)  Status.OK else Status.FAILED))
            }
    }

    fun onSaveCourse(data: CourseViewItem) {
        service.saveCourseViewItem(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                payload ->
                this.data.postValue(Status(payload, if (payload.uid != -1L)  Status.OK else Status.FAILED))
            }
    }

    fun onSaveSource(data: CourseSourceItem) {
        data.users.add("Extra item to test array storage")
        service.saveSourceForCourse(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                payload ->
                this.data.postValue(Status(payload, if (payload.source.uid != -1L)  Status.OK else Status.FAILED))
            }
    }

}