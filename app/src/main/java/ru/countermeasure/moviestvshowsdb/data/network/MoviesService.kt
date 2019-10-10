package ru.countermeasure.moviestvshowsdb.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.countermeasure.moviestvshowsdb.data.network.response.paged.movie_discover.MovieDiscoverResult
import ru.countermeasure.moviestvshowsdb.data.network.response.paged.ResponseDto
import ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_credits.MovieCreditsResponse
import ru.countermeasure.moviestvshowsdb.data.network.response.single.movie_detail.MovieDetailResponse

interface MoviesService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = "RU"
    ): Response<ResponseDto<MovieDiscoverResult>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = "RU"
    ): Response<ResponseDto<MovieDiscoverResult>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru-RU",
        @Query("region") region: String? = "RU"
    ): Response<ResponseDto<MovieDiscoverResult>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("language") language: String = "ru-RU"
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movie_id: Int
    ): Response<MovieCreditsResponse>
}