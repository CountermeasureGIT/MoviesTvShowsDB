package ru.countermeasure.moviestvshowsdb.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.countermeasure.moviestvshowsdb.model.db.entity.Movie
import ru.countermeasure.moviestvshowsdb.repository.MovieDiscoverRepository
import ru.countermeasure.moviestvshowsdb.util.Resource

class MainViewModel(
    //private val iMovieDiscover: IMovieDiscover
    private val movieDiscoverRepository: MovieDiscoverRepository
) : ViewModel() {

    val topRatedMovies: LiveData<Resource<List<Movie>>> = liveData {
        emitSource(movieDiscoverRepository.getTopRatedMovies())
    }

    private fun loadTopRatedMovies() {
        val scope = viewModelScope.launch {
            movieDiscoverRepository.getTopRatedMovies()
        }
    }

    fun updateMoviesClicked() {
        loadTopRatedMovies()
    }

}