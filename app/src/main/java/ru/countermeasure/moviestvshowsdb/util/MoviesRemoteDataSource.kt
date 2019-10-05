package ru.countermeasure.moviestvshowsdb.util

import ru.countermeasure.moviestvshowsdb.model.network.MoviesDBApi

class MoviesRemoteDataSource (
    private val service: MoviesDBApi
) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getTopRatedMovies(1) }

}
