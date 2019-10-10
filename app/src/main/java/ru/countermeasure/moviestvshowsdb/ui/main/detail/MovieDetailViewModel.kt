package ru.countermeasure.moviestvshowsdb.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.countermeasure.moviestvshowsdb.data.db.entity.Cast
import ru.countermeasure.moviestvshowsdb.data.db.entity.MovieDetail
import ru.countermeasure.moviestvshowsdb.data.util.Result
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository

class MovieDetailViewModel(
    private val movieDiscoverRepository: MovieDiscoverRepository
) : ViewModel() {
    private val movieId: MutableLiveData<Int> = MutableLiveData()
    private val movieDetail: LiveData<Result<MovieDetail>> =
        Transformations.switchMap(movieId) { movieId ->
            movieDiscoverRepository.getMovieDetail(movieId)
        }
    private val movieCredits: LiveData<Result<List<Cast>>> =
        Transformations.switchMap(movieId) { movieId ->
            movieDiscoverRepository.getMovieCredits(movieId)
        }

    fun setMovieId(id: Int) {
        movieId.value = id
    }

    fun getMovieDetail(): LiveData<Result<MovieDetail>> = movieDetail
    fun getMovieCredits(): LiveData<Result<List<Cast>>> = movieCredits
}