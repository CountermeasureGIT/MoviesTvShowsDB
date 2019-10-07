package ru.countermeasure.moviestvshowsdb.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.countermeasure.moviestvshowsdb.extension.toMovie
import ru.countermeasure.moviestvshowsdb.model.db.dao.TopRatedMoviesDao
import ru.countermeasure.moviestvshowsdb.model.db.entity.MovieCategoryType
import ru.countermeasure.moviestvshowsdb.model.db.entity.MovieToMovieCategory
import ru.countermeasure.moviestvshowsdb.model.network.MoviesRemoteDataSource
import ru.countermeasure.moviestvshowsdb.model.util.resultLiveData

class MovieDiscoverRepository(
    private val moviesDao: TopRatedMoviesDao,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    fun getTopRatedMovies() = resultLiveData(
        { moviesDao.getTopRatedMovies() },
        { moviesRemoteDataSource.fetchTopRatedMoviesData() },
        { responseDto ->
            moviesDao.saveMovies(
                responseDto.results.map { movieDiscoverResult ->
                    movieDiscoverResult.toMovie()
                }
            )
        }
    )

    fun getNowPlayingMovies() = resultLiveData(
        { moviesDao.getMoviesByCategory(MovieCategoryType.NEW) },
        { moviesRemoteDataSource.fetchNowPlayingMoviesData() },
        { responseDto ->
            val category = MovieCategoryType.NEW

            moviesDao.saveMovies(
                responseDto.results.map { movieDiscoverResult ->
                    movieDiscoverResult.toMovie()
                }
            )

            moviesDao.deleteMovieToMovieCategory(category)
            moviesDao.saveMoviesCategory(
                responseDto.results.map {
                    MovieToMovieCategory(it.id, category)
                }
            )
        }
    )

    fun getUpcomingMovies() = resultLiveData(
        { moviesDao.getMoviesByCategory(MovieCategoryType.SOON) },
        { moviesRemoteDataSource.fetchUpcomingMoviesData() },
        { responseDto ->
            val category = MovieCategoryType.SOON

            moviesDao.saveMovies(
                responseDto.results.map { movieDiscoverResult ->
                    movieDiscoverResult.toMovie()
                }
            )

            moviesDao.deleteMovieToMovieCategory(category)
            moviesDao.saveMoviesCategory(
                responseDto.results.map {
                    MovieToMovieCategory(it.id, category)
                }
            )
        }
    )
}