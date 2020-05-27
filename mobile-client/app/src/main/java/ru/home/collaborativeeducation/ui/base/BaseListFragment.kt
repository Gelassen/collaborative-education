package ru.home.collaborativeeducation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.ui.IModelListener

abstract class BaseListFragment<M : ViewModel, A : RecyclerView.Adapter<*>>: Fragment(), IModelListener {

    protected abstract val viewModel: M

    protected abstract val adapter: A

    protected abstract val getLayoutRes: Int

    /*protected abstract val getViewModelClass: Class<M>*/ // shall we use here a different wild card name?

    protected lateinit var list: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        list = view!!.findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(context)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        list.adapter = adapter
    }

    override fun onServerError() {
        list.post {
            Toast.makeText(context, getString(R.string.error_server_side), Toast.LENGTH_SHORT).show()
        }
    }

}