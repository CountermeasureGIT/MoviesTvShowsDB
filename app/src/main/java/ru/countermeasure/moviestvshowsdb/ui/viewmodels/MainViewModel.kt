package ru.countermeasure.moviestvshowsdb.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.countermeasure.moviestvshowsdb.model.network.IMovieDiscover
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResponse
import ru.countermeasure.moviestvshowsdb.model.network.response.movie_discover.MovieDiscoverResult

class MainViewModel(
    private val iMovieDiscover: IMovieDiscover
) : ViewModel() {

    private val topRatedMovies: MutableLiveData<List<MovieDiscoverResult>> by lazy {
        MutableLiveData<List<MovieDiscoverResult>>()
    }

    init {
        loadTopRatedMovie()
    }

    fun getTopRatedMovies(): LiveData<List<MovieDiscoverResult>> {
        return topRatedMovies
    }

    private fun loadTopRatedMovie() {
        viewModelScope.launch {
            var moviesResponse: Response<MovieDiscoverResponse>? = null
            try {
                moviesResponse = iMovieDiscover.getTopRatedMovies(1)
            } catch (e: Exception) {
                e.printStackTrace()
            }


            if (moviesResponse != null && moviesResponse.isSuccessful && moviesResponse.body() != null) {
                val results = moviesResponse.body()!!
                topRatedMovies.postValue(results.results)
            }

        }
    }

}