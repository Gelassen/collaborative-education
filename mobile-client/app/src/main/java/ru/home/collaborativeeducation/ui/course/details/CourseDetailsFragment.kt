package ru.home.collaborativeeducation.ui.course.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import ru.home.collaborativeeducation.AppApplication
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.model.CourseWithMetadataAndComments
import ru.home.collaborativeeducation.ui.addNew.AddNewActivity
import ru.home.collaborativeeducation.ui.addNew.source.AddSourceFragment
import ru.home.collaborativeeducation.ui.base.BaseListFragment

class CourseDetailsFragment : BaseListFragment<CourseDetailsViewModel, CourseDetailsAdapter>(), CourseDetailsAdapter.ClickListener {

    val REQUEST_CODE = 1002

    companion object {

        fun newInstance(
            data: CourseViewItem
        ) : CourseDetailsFragment {
            val fragment = CourseDetailsFragment()
            val args = Bundle()
            args.putParcelable(PAYLOAD_COURSE, data)
            fragment.arguments = args
            return fragment
        }

        val PAYLOAD_COURSE: String = "EXTRAS_PAYLOAD_COURSE"

        val TAG = CourseDetailsFragment::class.java.name

    }

    private lateinit var payloadCourse: CourseViewItem

    override val viewModel: CourseDetailsViewModel
        get() = ViewModelProviders.of(this).get(CourseDetailsViewModel::class.java)

    override val adapter: CourseDetailsAdapter
        get() = CourseDetailsAdapter()

    override val getLayoutRes: Int
        get() = R.layout.course_details_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        payloadCourse = arguments!!.getParcelable<CourseViewItem>(PAYLOAD_COURSE)!!

        (list.adapter as CourseDetailsAdapter).setListener(this)
        (list.adapter as CourseDetailsAdapter).onInit(context!!)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_sources_divider)!!)
        list.addItemDecoration(divider)

        viewModel.init(activity!!.application as AppApplication, this)
        viewModel.getModel().observe(this, Observer() {
            when(it.type) {
                CourseDetailsViewModel.DataWrapper.TYPE_DATA -> (list.adapter as CourseDetailsAdapter).update(it.data)
                CourseDetailsViewModel.DataWrapper.TYPE_UPDATE -> (list.adapter as CourseDetailsAdapter).updateItem(it.data.get(0))
            }
        })
        viewModel.onStart(payloadCourse.categoryUid, payloadCourse.uid!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1 && requestCode == REQUEST_CODE) {
            (list.adapter as CourseDetailsAdapter).addItem(data!!.getParcelableExtra<CourseWithMetadataAndComments>(AddSourceFragment.PAYLOAD)!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.category_menu, menu)
        menu.findItem(R.id.action_add_new).title = getString(R.string.action_add_new_source)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_add_new -> AddNewActivity.start(activity!!, AddNewActivity.Type.ADD_SOURCE, payloadCourse, REQUEST_CODE)
        }
        return true
    }

    override fun onItemClick(item: CourseSourceItem) {
/*        fragmentManager!!.beginTransaction()
            .replace(R.id.container, CourseFragment.newInstance(item))
            .addToBackStack(CourseFragment.TAG)
            .commit()*/
    }

    override fun onLikeClick(item: CourseWithMetadataAndComments) {
        viewModel.onLike(item)
    }

}