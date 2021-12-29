package com.project.mymovies.ui.home

import androidx.lifecycle.*
import com.project.mymovies.model.MoviesResult
import com.project.mymovies.utilites.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repo: HomeRepository) : ViewModel(), LifecycleObserver {

    val movies = repo.getMovies().asLiveData()
    val genre = repo.getGenre().asLiveData()

    private val _id = MutableLiveData<Long>()

    private val _genreMovies = _id.switchMap { id ->
        repo.getGenreMovies(id).asLiveData()
    }

    val genreMovies: LiveData<Resource<List<MoviesResult>>> = _genreMovies

    fun start(id: Long) {
        _id.value = id
    }
}