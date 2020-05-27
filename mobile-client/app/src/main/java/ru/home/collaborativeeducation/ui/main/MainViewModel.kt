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
import ru.home.collaborativeeducation.ui.IModelListener
import javax.inject.Inject

class MainViewModel: ViewModel() {

    @Inject
    lateinit var service: NetworkRepository

    @Inject
    lateinit var repo: InternalStorageRepository

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

    fun onStop() {
        disposables.clear()
    }

}
