package ru.countermeasure.moviestvshowsdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository
import ru.countermeasure.moviestvshowsdb.model.util.Result

class SharedViewModel(
    private val movieDiscoverRepository: MovieDiscoverRepository
) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val movies: LiveData<Result<List<Movie>>> =
        Transformations.switchMap(reloadTrigger) {
            movieDiscoverRepository.getTopRatedMovies()
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

    fun getTopRatedMovies(): LiveData<Result<List<Movie>>> = movies
}