package ru.countermeasure.moviestvshowsdb.extension

import ru.countermeasure.moviestvshowsdb.data.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieCategoryType
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieDetail
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieToMovieCategory
import ru.countermeasure.moviestvshowsdb.data.network.response.paged.movie_discover.MovieDiscoverResult
import ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_credits.Cast
import ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_detail.MovieDetailResponse

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
        genre_ids = genre_ids,
        title = title,
        vote_average = vote_average,
        overview = overview,
        release_date = release_date
    )
}

fun MovieDiscoverResult.toMovieToMovieCategory(type: MovieCategoryType): MovieToMovieCategory {
    return MovieToMovieCategory(
        movie_id = id,
        type = type
    )
}

fun MovieDetailResponse.toMovieDetail(): MovieDetail {
    return MovieDetail(
        adult = adult,
        backdrop_path = backdrop_path,
        budget = budget,
        genres = genres.joinToString(separator = ", ") { it.name },
        homepage = homepage,
        movie_id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        production_countries = production_countries.joinToString(separator = ", ") { it.name },
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        spoken_languages = spoken_languages.joinToString(separator = ", ") { it.name },
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun Cast.toCastEntity(movieId: Int): ru.countermeasure.moviestvshowsdb.data.db.entity.Cast {
    return ru.countermeasure.moviestvshowsdb.data.db.entity.Cast(
        movie_id = movieId,
        order = order,
        name = name,
        profile_path = profile_path,
        character = character
    )
}