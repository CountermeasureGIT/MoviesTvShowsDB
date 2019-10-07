package ru.countermeasure.moviestvshowsdb.model.network

class MoviesRemoteDataSource(
    private val service: MoviesService
) : BaseDataSource() {

    suspend fun fetchTopRatedMoviesData() = getResult { service.getTopRatedMovies(1) }

    suspend fun fetchNowPlayingMoviesData() = getResult { service.getNowPlayingMovies(1) }

    suspend fun fetchUpcomingMoviesData() = getResult { service.getUpcomingMovies(1) }

}
