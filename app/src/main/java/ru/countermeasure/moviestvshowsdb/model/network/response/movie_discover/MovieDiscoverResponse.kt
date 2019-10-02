package ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover

data class MovieDiscoverResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieDiscoverResult>
)