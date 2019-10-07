package ru.countermeasure.moviestvshowsdb.ui.new_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository
import ru.countermeasure.moviestvshowsdb.util.Result

class NewMoviesViewModel(moviesRepository: MovieDiscoverRepository) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val topRatedMovies: LiveData<Result<List<Movie>>> =
        Transformations.switchMap(reloadTrigger) {
            moviesRepository.getTopRatedMovies()
        }

    init {
        refreshTopRatedMovies()
    }

    private fun refreshTopRatedMovies() {
        reloadTrigger.value = true
    }

    fun onRefreshAction() {
        refreshTopRatedMovies()
    }

    fun getTopRatedMovies(): LiveData<Result<List<Movie>>> = topRatedMovies

}