package ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_credits

data class Cast(
    val character: String,
    val name: String,
    val order: Int,
    val profile_path: String?
)