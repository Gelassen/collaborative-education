package ru.home.collaborativeeducation.ui.course

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
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.ui.addNew.AddNewActivity
import ru.home.collaborativeeducation.ui.addNew.source.AddSourceFragment
import ru.home.collaborativeeducation.ui.base.BaseListFragment
import ru.home.collaborativeeducation.ui.base.SearchTextWatcher
import ru.home.collaborativeeducation.ui.course.details.CourseDetailsFragment

class CourseFragment : BaseListFragment<CourseViewModel, CourseAdapter>(),
    CourseAdapter.ClickListener {

    val REQUEST_CODE = 1001

    companion object {

        fun newInstance(item: CategoryViewItem): CourseFragment {
            val fragment = CourseFragment()
            val args = Bundle()
            args.putParcelable(PAYLOAD, item)
            fragment.arguments = args
            return fragment
        }

        val PAYLOAD: String = "EXTRA_PAYLOAD"

        val TAG: String = CourseFragment::class.java.name
    }

    lateinit var category: CategoryViewItem

    override val viewModel: CourseViewModel
        get() = ViewModelProviders.of(this).get(CourseViewModel::class.java)

    override val adapter: CourseAdapter
        get() = CourseAdapter()

    override val getLayoutRes: Int
        get() = R.layout.category_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        category = arguments!!.getParcelable<CategoryViewItem>(PAYLOAD)!!

        (list.adapter as CourseAdapter).setListener(this)

        search.addTextChangedListener(object: SearchTextWatcher() {
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                (list.adapter as CourseAdapter).filter.filter(string)
            }
        })

        viewModel.init(activity!!.application as AppApplication, this)
        viewModel.getModel().observe(this, Observer() {
            val datasource: MutableList<CourseViewItem> = ArrayList()
            datasource.addAll(it)
            (list.adapter as CourseAdapter).update(datasource)
        })
        viewModel.onStart(category!!.uid!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1 && requestCode == REQUEST_CODE) {
            (list.adapter as CourseAdapter).addItem(data!!.getParcelableExtra<CourseViewItem>(AddSourceFragment.PAYLOAD)!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.category_menu, menu)
        menu.findItem(R.id.action_add_new).title = getString(R.string.action_add_new_course)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        val category = arguments!!.getParcelable<CategoryViewItem>(PAYLOAD)
        when (item.itemId) {
            R.id.action_add_new -> AddNewActivity.start(activity!!, AddNewActivity.Type.ADD_COURSE, category, REQUEST_CODE)
        }
        return true
    }

    override fun onItemClick(item: CourseViewItem) {
        fragmentManager!!.beginTransaction()
            .replace(R.id.container, CourseDetailsFragment.newInstance(item))
            .addToBackStack(CourseDetailsFragment.TAG)
            .commit()
    }
}