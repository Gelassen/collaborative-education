package ru.home.collaborativeeducation.storage.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CollectionToStringConverter {

    @TypeConverter
    fun stringToCollection(json: String): ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun collectionsToString(comments: ArrayList<String>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.toJson(comments, type)
    }
}