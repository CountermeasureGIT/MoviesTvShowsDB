package ru.countermeasure.moviestvshowsdb.model.network

import ru.countermeasure.moviestvshowsdb.util.BaseDataSource

class MovesRemoteDataSource (private val moviesDBApi: MoviesDBApi) : BaseDataSource() {

    suspend fun fetchData() = getResult { moviesDBApi.getTopRatedMovies(1) }
}