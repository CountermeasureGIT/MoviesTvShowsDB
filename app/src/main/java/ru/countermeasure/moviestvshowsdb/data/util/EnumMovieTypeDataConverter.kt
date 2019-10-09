package ru.countermeasure.moviestvshowsdb.data.util

import androidx.room.TypeConverter
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieCategoryType

class EnumMovieTypeDataConverter {

    @TypeConverter
    fun fromEnumMovieType(value: MovieCategoryType): Int {
        return value.type_id
    }

    @TypeConverter
    fun toEnumMovieType(value: Int): MovieCategoryType {
        return MovieCategoryType.valueOf(value.toString())
    }
}