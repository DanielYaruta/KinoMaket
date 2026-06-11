package com.example.kinomaket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesViewModel : ViewModel() {

    private val _selectedMovieId = MutableLiveData(MovieRepository.movies.first().id)
    val selectedMovieId: LiveData<Int> = _selectedMovieId

    fun selectMovie(id: Int) {
        _selectedMovieId.value = id
    }
}
