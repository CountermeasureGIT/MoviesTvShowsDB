package ru.countermeasure.moviestvshowsdb.util.temp

data class ErrorResponse(
    val status_message: String,
    val success: Boolean,
    val status_code: Int
)