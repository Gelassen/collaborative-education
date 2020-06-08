package ru.home.collaborativeeducation.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.home.collaborativeeducation.App
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.network.NetworkRepository
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.storage.Cache
import ru.home.collaborativeeducation.ui.IModelListener
import javax.inject.Inject
import kotlin.collections.ArrayList

const val THIRTY_DAYS : Long = 2592000

class MainViewModel: ViewModel() {

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

    @Inject
    lateinit var cache: Cache

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var listener: IModelListener? = null

    private val data: MutableLiveData<List<CategoryViewItem>> = MutableLiveData()

    fun init(app: AppApplication, listener: IModelListener) {
        app.getComponent().inject(this)
        this.listener = listener
    }

    fun getModel(): MutableLiveData<List<CategoryViewItem>> {
        return data
    }

    fun onStart() {
        checkReminder()

        disposables.add(
            service.getCategoryViewItems()
                .doOnError { it ->
                    if (listener != null) {
                        listener!!.onServerError()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map{data ->
                    val result: MutableList<CategoryViewItem> = ArrayList()
                    for (item in data) {
                        result.add(CategoryViewItem(item.uid, item.title))
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
        )
    }

    fun confirmReminderShowUp() {
        cache.saveTimeReminder()
    }

    fun onStop() {
        disposables.clear()
    }

    private fun checkReminder() {
        if (listener == null) return

        val lastView = cache.getTimeReminder()
        val currentTime  = System.currentTimeMillis()
        val period = (currentTime - lastView) / 1000
        Log.d(App.TAG, String.format("LastView %s, CurrentTime %s, Period %s ", lastView, currentTime, period))
        if (period > THIRTY_DAYS) {
            listener!!.onShowReminder()
        }
    }

}
