package com.project.mymovies.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.project.mymovies.model.Genre


class Converters {
    @TypeConverter
    fun listToJson(value: List<Long>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Long>::class.java).toList()

    @TypeConverter
    fun listGenreToJson(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGenre(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()

}