package com.project.mymovies.network

import com.project.mymovies.model.MovieDetails
import com.project.mymovies.model.MovieGenre
import com.project.mymovies.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("discover/movie")
    fun getAllMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Call<Movies>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Long , @Query("api_key") api_key: String): Call<MovieDetails>

    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") api_key: String): Call<MovieGenre>


}