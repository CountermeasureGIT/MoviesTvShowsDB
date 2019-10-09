package ru.countermeasure.moviestvshowsdb.data.network

class MoviesRemoteDataSource(
    private val service: MoviesService
) : BaseDataSource() {

    suspend fun fetchTopRatedMoviesData(page: Int = 1) =
        getResult { service.getTopRatedMovies(page) }

    suspend fun fetchNowPlayingMoviesData(page: Int = 1) =
        getResult { service.getNowPlayingMovies(page) }

    suspend fun fetchUpcomingMoviesData(page: Int = 1) =
        getResult { service.getUpcomingMovies(page) }

    suspend fun fetchMovieDetailData(movieId: Int) =
        getResult { service.getMovieDetail(movieId) }

}
