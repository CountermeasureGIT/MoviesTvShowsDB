package ru.countermeasure.moviestvshowsdb.extension

import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResult

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