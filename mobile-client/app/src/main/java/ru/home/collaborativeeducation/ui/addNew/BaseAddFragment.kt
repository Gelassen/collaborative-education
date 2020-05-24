package ru.home.collaborativeeducation.ui.addNew

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.add_new_item_fragment.*
import ru.home.collaborativeeducation.App
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.network.model.Payload
import ru.home.collaborativeeducation.repository.InternalStorageRepository
import ru.home.collaborativeeducation.ui.IModelListener

abstract class BaseAddFragment : Fragment(), IModelListener {

    interface AddSourceListener {
        fun onSaveItem(item: Parcelable)
    }

    protected lateinit var viewModel: AddNewViewModel

    protected abstract val getLayoutRes: Int

    protected lateinit var listener: AddSourceListener

    protected var selectedItem: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNewViewModel::class.java)
        viewModel.init(activity!!.application as AppApplication, this)
        viewModel.getModel().observe(this, Observer {
            when(it.status) {
                AddNewViewModel.Status.OK -> onAddNewItemClick(it.payload) // TODO extend with data model
                AddNewViewModel.Status.FAILED -> onFailedToAddNewItemClick()
            }
        })

        save.setOnClickListener {
            onSave()
        }

        cancel.setOnClickListener {
            onCancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    fun setAddSourceListener(listener: AddSourceListener) {
        this.listener = listener
    }

    abstract fun onSave()

    fun onCancel() {
        activity!!.finish()
    }

    open fun onAddNewItemClick(addedItem: Parcelable?) {
        listener.onSaveItem(if (addedItem == null) selectedItem!! else addedItem)
        activity!!.finish()
    }

    fun onFailedToAddNewItemClick() {
        Toast.makeText(context!!, "Can't to add new item. Contact admin", Toast.LENGTH_SHORT).show()
    }

    override fun onServerError() {
        view!!.post {
            Toast.makeText(context, "Something went wrong on backend. Please verify input data or try later", Toast.LENGTH_SHORT).show()
        }
    }
}