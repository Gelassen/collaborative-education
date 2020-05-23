package ru.home.collaborativeeducation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import ru.home.collaborativeeducation.App
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.ui.base.BaseActivity
import ru.home.collaborativeeducation.ui.main.CategoryFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CategoryFragment.newInstance())
                .commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(App.TAG, "onActivityResult: ")

        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
