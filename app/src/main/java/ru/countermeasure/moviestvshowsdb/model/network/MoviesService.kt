package ru.countermeasure.moviestvshowsdb.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResult
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.ResponseDto

interface MoviesService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = null
    ) : Response<ResponseDto<MovieDiscoverResult>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = null
    ) : Response<ResponseDto<MovieDiscoverResult>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = null
    ) : Response<ResponseDto<MovieDiscoverResult>>

}