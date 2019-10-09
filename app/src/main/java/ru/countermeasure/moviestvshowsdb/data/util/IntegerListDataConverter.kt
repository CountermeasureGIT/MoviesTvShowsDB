package ru.countermeasure.moviestvshowsdb.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListDataConverter {

    @TypeConverter
    fun fromIntegerList(value: List<Int>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntegerList(value: String): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}
