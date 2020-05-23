package ru.home.collaborativeeducation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_fragment.*
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.ui.addNew.AddNewActivity
import ru.home.collaborativeeducation.ui.addNew.source.AddSourceFragment
import ru.home.collaborativeeducation.ui.base.BaseListFragment
import ru.home.collaborativeeducation.ui.base.SearchTextWatcher
import ru.home.collaborativeeducation.ui.course.CourseFragment

class CategoryFragment : BaseListFragment<MainViewModel, MainAdapter>(), MainAdapter.ClickListener {

    val REQUEST_CODE = 1000

    companion object {
        fun newInstance() = CategoryFragment()

        val TAG: String = CategoryFragment::class.java.name
    }

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    override val adapter: MainAdapter
        get() = MainAdapter()

    override val getLayoutRes: Int
        get() = R.layout.main_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (list.adapter as MainAdapter).setListener(this)

        search.addTextChangedListener(object: SearchTextWatcher() {
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                (list.adapter as MainAdapter).filter.filter(string)
            }
        })

        viewModel.init(activity!!.application as AppApplication)
        viewModel.getModel().observe(this, Observer() {
            val datasource: MutableList<CategoryViewItem> = ArrayList()
            datasource.addAll(it)
            (list.adapter as MainAdapter).update(datasource)
        })
        viewModel.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1 && requestCode == REQUEST_CODE) {
            (list.adapter as MainAdapter).addItem(data!!.getParcelableExtra<CategoryViewItem>(
                AddSourceFragment.PAYLOAD)!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.category_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_add_new -> AddNewActivity.start(activity!!, AddNewActivity.Type.ADD_CATEGORY, null, REQUEST_CODE)
        }
        return true
    }

    override fun onCategoryClick(item: CategoryViewItem) {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container, CourseFragment.newInstance(item))
            .addToBackStack(CourseFragment.TAG)
            .commit()
    }

}
