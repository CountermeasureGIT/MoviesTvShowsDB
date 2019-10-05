package ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover

import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie

data class MovieDiscoverResult(
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String?,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
)

fun MovieDiscoverResult.toMovie(): Movie {
    return Movie(
        id = id,
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        poster_path = poster_path,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        title = title,
        vote_average = vote_average,
        overview = overview,
        release_date = release_date
    )
}