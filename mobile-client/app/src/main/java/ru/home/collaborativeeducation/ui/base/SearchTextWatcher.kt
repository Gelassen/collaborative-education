package ru.home.collaborativeeducation.ui.base

import android.text.Editable
import android.text.TextWatcher

abstract class SearchTextWatcher : TextWatcher {

    override fun afterTextChanged(string: Editable?) {}

    override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {}
}