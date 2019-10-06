package ru.countermeasure.moviestvshowsdb.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResult
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.ResponseDto

interface MoviesService {

    @GET("discover/movie")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("language") language: String = "ru-RU"
    ) : Response<ResponseDto<MovieDiscoverResult>>
}