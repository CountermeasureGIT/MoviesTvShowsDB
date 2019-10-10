package ru.countermeasure.moviestvshowsdb.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.countermeasure.moviestvshowsdb.BuildConfig

@Entity(
    tableName = "movie_detail",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id")
        )
    )
)
data class MovieDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val genres: String,
    val homepage: String?,
    val movie_id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_countries: String,
    val release_date: String,
    val revenue: String,
    val runtime: Int?,
    val spoken_languages: String,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun getPosterUrl(): String? {
        var url: String? = null
        if (!poster_path.isNullOrBlank()) {
            url =
                "${BuildConfig.BASE_IMAGE_API_URL}/w500/$poster_path" +
                        "?${BuildConfig.API_KEY_QUERY_NAME}=${BuildConfig.API_KEY}"
        }
        return url
    }

    fun getBackdropUrl(): String? {
        var url: String? = null
        if (!backdrop_path.isNullOrBlank()) {
            url =
                "${BuildConfig.BASE_IMAGE_API_URL}/w780/$backdrop_path" +
                        "?${BuildConfig.API_KEY_QUERY_NAME}=${BuildConfig.API_KEY}"
        }
        return url
    }
}