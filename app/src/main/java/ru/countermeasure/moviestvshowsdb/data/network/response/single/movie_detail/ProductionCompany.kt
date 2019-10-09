package ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_detail

data class ProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)