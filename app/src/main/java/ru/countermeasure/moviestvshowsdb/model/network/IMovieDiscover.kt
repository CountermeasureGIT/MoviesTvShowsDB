package ru.countermeasure.moviestvshowsdb.model.network

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResponse
import ru.countermeasure.moviestvshowsdb.util.ApiResponse

interface IMovieDiscover {

    @GET("discover/movie")
    fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("language") language: String = "ru-RU"
    ) : LiveData<ApiResponse<MovieDiscoverResponse>>
}