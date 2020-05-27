package ru.home.collaborativeeducation.ui.addNew.course

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.add_new_datasource_fragment.*
import kotlinx.android.synthetic.main.add_new_item_fragment.*
import kotlinx.android.synthetic.main.add_new_item_fragment.categoryNameInput
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.ui.addNew.BaseAddFragment

class AddCourseFragment : BaseAddFragment() {

    companion object {

        fun newInstance(data: CategoryViewItem): AddCourseFragment {
            val fragment = AddCourseFragment()
            val args = Bundle()
            args.putParcelable(PAYLOAD, data)
            fragment.arguments = args
            return fragment
        }

        val PAYLOAD = "EXTRA_PAYLOAD"
    }

    override val getLayoutRes: Int
        get() = R.layout.add_new_item_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryNameInput.hint = getString(R.string.hint_add_course)
        hint.text = getString(R.string.hint_add_course)
    }

    override fun onSave() {
        selectedItem = CourseViewItem(
            0,
            categoryNameInput.text.toString(),
            arguments!!.getParcelable<CategoryViewItem>(PAYLOAD)!!.uid!!)

        viewModel.onSaveCourse(selectedItem as CourseViewItem)
    }
}