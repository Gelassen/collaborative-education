package ru.home.collaborativeeducation.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.util.*

class Cache(context: Context) {

    val pref: SharedPreferences

    companion object {
        val KEY_UUID: String = "KEY_UUID"
    }

    init {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveUuid() {
        pref.edit()
            .putString(KEY_UUID, UUID.randomUUID().toString())
            .apply()
    }

    fun getUuid(): String {
        return pref.getString(KEY_UUID, "")!!
    }
}