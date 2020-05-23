package ru.home.collaborativeeducation.ui.addNew.source

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.add_new_datasource_fragment.*
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CourseSourceItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.storage.Cache
import ru.home.collaborativeeducation.ui.addNew.BaseAddFragment

class AddSourceFragment : BaseAddFragment() {

    companion object {

        fun newInstance(data: CourseViewItem): AddSourceFragment {
            val fragment = AddSourceFragment()
            val args = Bundle()
            args.putParcelable(PAYLOAD, data)
            fragment.arguments = args
            return fragment
        }

        val PAYLOAD = "EXTRA_PAYLOAD"
    }

    override val getLayoutRes: Int
        get() = R.layout.add_new_datasource_fragment


    override fun onSave() {
        selectedItem = CourseSourceItem(
            0,
            categoryNameInput.text.toString(),
            datasourceInput.text.toString(),
            arguments!!.getParcelable<CourseViewItem>(PAYLOAD)!!.uid!!,
            arrayListOf<String>(Cache(context!!).getUuid()))

        viewModel.onSaveSource(selectedItem as CourseSourceItem)
    }

}