package ru.home.collaborativeeducation.ui.course

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.App
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import javax.inject.Inject

class CourseViewModel : ViewModel() {

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

    private val data: MutableLiveData<List<CourseViewItem>> = MutableLiveData()

    fun init(app: AppApplication) {
        app.getComponent().inject(this)
    }

    fun getModel(): MutableLiveData<List<CourseViewItem>> {
        return data
    }

    fun onStart(id: Long) {
        service.getCoursesForCategory(id.toString())
            .map{data ->
                val result: MutableList<CourseViewItem> = ArrayList()
                for (item in data) {
                    result.add(CourseViewItem(item.uid, item.title, item.categoryUid))
                }
                result
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        data -> this.data.postValue(data)
                },
                {
                        it -> Log.e(App.TAG, "Error was thrown", it)
                }
            )
    }

}