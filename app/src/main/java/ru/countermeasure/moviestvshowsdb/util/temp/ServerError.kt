package ru.countermeasure.moviestvshowsdb.util.temp

data class ServerError(
    val code: Int,
    val errorBody: String?
) : RuntimeException()