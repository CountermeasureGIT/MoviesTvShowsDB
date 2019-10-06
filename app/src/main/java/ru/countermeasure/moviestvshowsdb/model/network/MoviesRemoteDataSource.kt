package ru.countermeasure.moviestvshowsdb.model.network

class MoviesRemoteDataSource(
    private val service: MoviesService
) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getTopRatedMovies(1) }

}
