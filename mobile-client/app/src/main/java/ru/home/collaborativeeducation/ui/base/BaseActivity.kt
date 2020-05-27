package ru.home.collaborativeeducation.ui.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ru.home.collaborativeeducation.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // app exploit options menu on toolbar
        /*if (supportActionBar != null) {
            supportActionBar!!.hide()
        }*/

        if (supportActionBar != null) {
            val drawable = ColorDrawable(resources.getColor(R.color.colorAccent))
            (supportActionBar as ActionBar).setBackgroundDrawable(drawable)
        }
    }

}