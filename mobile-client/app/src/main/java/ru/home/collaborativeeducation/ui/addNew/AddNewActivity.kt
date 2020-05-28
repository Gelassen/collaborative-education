package ru.home.collaborativeeducation.ui.addNew

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.model.CategoryViewItem
import ru.home.collaborativeeducation.model.CourseViewItem
import ru.home.collaborativeeducation.ui.addNew.category.AddCategoryFragment
import ru.home.collaborativeeducation.ui.addNew.course.AddCourseFragment
import ru.home.collaborativeeducation.ui.addNew.source.AddSourceFragment
import ru.home.collaborativeeducation.ui.base.BaseActivity

class AddNewActivity : BaseActivity(), BaseAddFragment.AddSourceListener {

    companion object {

        fun<T : Parcelable?> start(context: FragmentActivity, type: Type, payload: T, requestCode: Int) {
            val intent = Intent(context, AddNewActivity::class.java)
            intent.putExtra(EXTRA_PAYLOAD, payload)
            intent.putExtra(EXTRA_SCREEN_TYPE, type)
            intent.putExtra(EXTRA_REQUEST_CODE, requestCode)
            context.startActivityForResult(intent, requestCode)
        }

        val EXTRA_SCREEN_TYPE = "EXTRA_SCREEN_TYPE"
        val EXTRA_PAYLOAD = "EXTRA_PAYLOAD"
        val EXTRA_REQUEST_CODE = "EXTRA_REQUEST_CODE"
    }

    enum class Type {
        ADD_CATEGORY,
        ADD_COURSE,
        ADD_SOURCE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_item_activity)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val type = intent.extras!!.get(EXTRA_SCREEN_TYPE)

        if (savedInstanceState == null) {
            var fragment: Fragment?
            when(type) {
                Type.ADD_CATEGORY -> fragment = AddCategoryFragment.newInstance()
                Type.ADD_COURSE -> fragment = AddCourseFragment.newInstance(intent.extras!!.get(EXTRA_PAYLOAD) as CategoryViewItem)
                Type.ADD_SOURCE -> fragment = AddSourceFragment.newInstance(intent.extras!!.get(EXTRA_PAYLOAD) as CourseViewItem)
                else -> fragment = Fragment()
            }

            if (fragment is BaseAddFragment) {
                fragment.setAddSourceListener(this)
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }

    override fun onSaveItem(item: Parcelable) {
        intent.putExtra(EXTRA_PAYLOAD, item)
        setResult(-1, intent)
        finishActivity(intent.getIntExtra(EXTRA_REQUEST_CODE, 0))
    }

}