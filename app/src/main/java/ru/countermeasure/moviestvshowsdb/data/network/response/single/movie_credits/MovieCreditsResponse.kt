package ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_credits

data class MovieCreditsResponse(
    val id: Int,
    val cast: List<Cast>
)