package ru.countermeasure.moviestvshowsdb.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieDetailViewModel : ViewModel() {
    private val movieId: MutableLiveData<Int> = MutableLiveData()
    //private val movieDetails: LiveData<MovieDetail>

    fun setMovieId(id: Int) {
        movieId.value = id
    }

}