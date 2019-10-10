package ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_credits

data class Crew(
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    val profile_path: String?
)