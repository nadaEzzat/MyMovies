package com.project.mymovies.ui.details

import androidx.lifecycle.*
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.utilites.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(repo: DetailsRepo) : ViewModel(), LifecycleObserver {

    private val _id = MutableLiveData<Long>()

    private val _movie = _id.switchMap { id ->
        repo.getMovieDetails(id).asLiveData()
    }

    val movie: LiveData<Resource<MovieDetails>> = _movie

    fun start(id: Long) {
        _id.value = id
    }

}