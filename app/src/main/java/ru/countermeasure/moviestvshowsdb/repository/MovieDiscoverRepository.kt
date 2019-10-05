package ru.countermeasure.moviestvshowsdb.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.countermeasure.moviestvshowsdb.model.db.dao.TopRatedMoviesDao
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.model.network.IMovieDiscover
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResponse
import ru.countermeasure.moviestvshowsdb.util.ApiResponse
import ru.countermeasure.moviestvshowsdb.util.NetworkBoundResource

class MovieDiscoverRepository(
    private val iMovieDiscover: IMovieDiscover,
    private val topRatedMoviesDao: TopRatedMoviesDao
) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + Dispatchers.IO
    //val errorHandler = ErrorHandler(GsonBuilder().create(), StringResource())

    fun getTopRatedMovies() = object :
        NetworkBoundResource<List<Movie>, MovieDiscoverResponse>(
            coroutineContext
        ) {
        override fun saveCallResult(item: MovieDiscoverResponse) {
            val results = item.results
            val movies = results.map {
                Movie(
                    it.id, it.popularity, it.vote_count, it.video, it.poster_path,
                    it.adult, it.backdrop_path, it.original_language, it.original_title,
                    //it.genre_ids,
                    it.title, it.vote_average, it.overview, it.release_date
                )
            }
            topRatedMoviesDao.saveMovies(movies)
        }

        override fun loadFromDb(): LiveData<List<Movie>> {
            return topRatedMoviesDao.getMovies()
        }

        override fun createCall(): LiveData<ApiResponse<MovieDiscoverResponse>> {
            return iMovieDiscover.getTopRatedMovies(1)
        }
    }.asLiveData()
}