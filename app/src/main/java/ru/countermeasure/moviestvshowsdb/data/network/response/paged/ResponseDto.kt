package ru.countermeasure.moviestvshowsdb.data.network.response.paged

data class ResponseDto<T>(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<T>
)