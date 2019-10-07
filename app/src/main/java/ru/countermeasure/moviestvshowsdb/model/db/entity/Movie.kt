package ru.countermeasure.moviestvshowsdb.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.countermeasure.moviestvshowsdb.BuildConfig
import ru.countermeasure.moviestvshowsdb.model.util.IntegerListDataConverter

@Entity(tableName = "movies")
@TypeConverters(IntegerListDataConverter::class)
data class Movie(
    @PrimaryKey
    val id: Int,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String?,
    val adult: Boolean,
    val backdrop_path: String?,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
) {
    fun getPosterPathUrl(): String? {
        var url: String? = null
        if (!poster_path.isNullOrBlank()) {
            url =
                "${BuildConfig.BASE_IMAGE_API_URL}/w185/$poster_path" +
                        "?${BuildConfig.API_KEY_QUERY_NAME}=${BuildConfig.API_KEY}"
        }
        return url
    }
}