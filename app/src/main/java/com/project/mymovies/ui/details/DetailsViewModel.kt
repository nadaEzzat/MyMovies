package com.project.mymovies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.model.MovieGenre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel constructor(private val repository: DetailsRepo) : ViewModel() {

    val movieList = MutableLiveData<MovieDetails>()
    val movieGenre = MutableLiveData<MovieGenre>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieDetails(api_key: String, id: Long) {

        val response = repository.getMovieDetails(api_key, id)
        response.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })


    }
    fun getMovieGenre(api_key: String)
    {

        val response2 = repository.getGenre(api_key)
        response2.enqueue(object : Callback<MovieGenre> {
            override fun onResponse(call: Call<MovieGenre>, response: Response<MovieGenre>) {
                movieGenre.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieGenre>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}