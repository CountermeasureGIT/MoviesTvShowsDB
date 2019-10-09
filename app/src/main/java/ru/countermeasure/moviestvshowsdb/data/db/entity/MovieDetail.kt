package ru.countermeasure.moviestvshowsdb.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: String,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)