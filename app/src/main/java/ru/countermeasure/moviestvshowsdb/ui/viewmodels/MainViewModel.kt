package ru.countermeasure.moviestvshowsdb.ui.viewmodels

import androidx.lifecycle.*
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository
import ru.countermeasure.moviestvshowsdb.util.Result

class MainViewModel(
    private val movieDiscoverRepository: MovieDiscoverRepository
) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val topRatedMovies: LiveData<Result<List<Movie>>> =
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

    fun getTopRatedMovies(): LiveData<Result<List<Movie>>> = topRatedMovies

}