package ru.home.collaborativeeducation.ui.addNew.category

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.add_new_item_fragment.*
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.ui.addNew.BaseAddFragment

class AddCategoryFragment : BaseAddFragment() {

    companion object {

        fun newInstance(): AddCategoryFragment {
            val fragment = AddCategoryFragment()
            return fragment
        }

    }

    override val getLayoutRes: Int
        get() = R.layout.add_new_item_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryNameInput.hint = getString(R.string.hint_add_category)
    }

    override fun onSave() {
        selectedItem = CategoryViewItem(0, categoryNameInput.text.toString())
        viewModel.onSaveCategory(selectedItem as CategoryViewItem)
    }
}