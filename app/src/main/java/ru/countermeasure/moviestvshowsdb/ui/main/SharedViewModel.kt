package ru.countermeasure.moviestvshowsdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.countermeasure.moviestvshowsdb.data.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository
import ru.countermeasure.moviestvshowsdb.data.util.Result
import ru.countermeasure.moviestvshowsdb.util.DoubleLiveData

enum class AppScreen {
    POPULAR,
    NEW,
    SOON
}

class SharedViewModel(
    private val movieDiscoverRepository: MovieDiscoverRepository
) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>(false)
    private val screenChangedTrigger = MutableLiveData<AppScreen>(AppScreen.POPULAR)

    private val doubleTrigger: DoubleLiveData<Boolean, AppScreen> =
        DoubleLiveData(reloadTrigger, screenChangedTrigger)

    private val movies: LiveData<Result<List<Movie>>> =
        Transformations.switchMap(doubleTrigger) {
            when (it.second) {
                AppScreen.POPULAR -> {
                    movieDiscoverRepository.getTopRatedMovies()
                }
                AppScreen.NEW -> {
                    movieDiscoverRepository.getNowPlayingMovies()
                }
                AppScreen.SOON -> {
                    movieDiscoverRepository.getUpcomingMovies()
                }
                else -> null
            }
        }

    init {
        refreshMovies()
    }

    fun onRefreshAction() {
        refreshMovies()
    }

    fun screenChanged(currentScreen: AppScreen) {
        triggerScreenChange(currentScreen)
    }

    private fun refreshMovies() {
        reloadTrigger.value = true
    }

    private fun triggerScreenChange(newScreen: AppScreen) {
        screenChangedTrigger.value = newScreen
    }

    fun getTopRatedMovies(): LiveData<Result<List<Movie>>> = movies
}