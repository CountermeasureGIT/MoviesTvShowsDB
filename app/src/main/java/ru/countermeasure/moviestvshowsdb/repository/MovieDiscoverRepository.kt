package ru.countermeasure.moviestvshowsdb.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.countermeasure.moviestvshowsdb.model.db.dao.TopRatedMoviesDao
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.toMovie
import ru.countermeasure.moviestvshowsdb.model.network.MoviesRemoteDataSource
import ru.countermeasure.moviestvshowsdb.util.resultLiveData

class MovieDiscoverRepository(
    private val moviesDao: TopRatedMoviesDao,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    fun getTopRatedMovies() = resultLiveData(
        { moviesDao.getMovies() },
        { moviesRemoteDataSource.fetchData() },
        { responseDto ->
            moviesDao.saveMovies(
                responseDto.results.map { movieDiscoverResult ->
                    movieDiscoverResult.toMovie()
                }
            )
        }
    )
}