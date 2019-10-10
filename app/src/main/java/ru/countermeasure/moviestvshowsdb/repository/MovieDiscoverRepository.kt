package ru.countermeasure.moviestvshowsdb.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.countermeasure.moviestvshowsdb.extension.toMovie
import ru.countermeasure.moviestvshowsdb.data.db.dao.MoviesDao
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieCategoryType
import ru.countermeasure.moviestvshowsdb.data.network.MoviesRemoteDataSource
import ru.countermeasure.moviestvshowsdb.data.util.resultLiveData
import ru.countermeasure.moviestvshowsdb.extension.toCastEntity
import ru.countermeasure.moviestvshowsdb.extension.toMovieDetail
import ru.countermeasure.moviestvshowsdb.extension.toMovieToMovieCategory

class MovieDiscoverRepository(
    private val moviesDao: MoviesDao,
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
            moviesDao.saveMovies(responseDto.results.map { movieDiscoverResult ->
                movieDiscoverResult.toMovie()
            })

            val category = MovieCategoryType.NEW
            moviesDao.replaceMovieToCategoryList(responseDto.results.map { movieDiscoverResult ->
                movieDiscoverResult.toMovieToMovieCategory(category)
            }, category)
        }
    )

    fun getUpcomingMovies() = resultLiveData(
        { moviesDao.getMoviesByCategory(MovieCategoryType.SOON) },
        { moviesRemoteDataSource.fetchUpcomingMoviesData() },
        { responseDto ->
            moviesDao.saveMovies(responseDto.results.map { movieDiscoverResult ->
                movieDiscoverResult.toMovie()
            })

            val category = MovieCategoryType.SOON
            moviesDao.replaceMovieToCategoryList(responseDto.results.map { movieDiscoverResult ->
                movieDiscoverResult.toMovieToMovieCategory(category)
            }, category)
        }
    )

    fun getMovieDetail(movieId: Int) = resultLiveData(
        { moviesDao.getMovieDetail(movieId) },
        { moviesRemoteDataSource.fetchMovieDetailData(movieId) },
        { responseDto ->
            moviesDao.saveMovieDetail(responseDto.toMovieDetail())
        }
    )

    fun getMovieCredits(movieId: Int) = resultLiveData(
        { moviesDao.getMovieCredits(movieId) },
        { moviesRemoteDataSource.fetchMovieCreditsData(movieId) },
        { responseDto ->
            moviesDao.saveMovieCredits(
                responseDto.cast.subList(
                    0,
                    responseDto.cast.size.coerceAtMost(20)
                ).map { cast ->
                    cast.toCastEntity(movieId)
                })
        }
    )

}