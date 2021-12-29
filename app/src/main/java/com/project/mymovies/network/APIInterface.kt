package com.project.mymovies.network

import com.project.mymovies.model.MovieDetails
import com.project.mymovies.model.MovieGenre
import com.project.mymovies.model.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("api_key") api_key: String, @Query("page")
        page: Int
    ): Movies

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
        @Query("api_key") api_key: String
    ): MovieDetails

    @GET("genre/movie/list")
    suspend fun getGenre(@Query("api_key") api_key: String): MovieGenre

    companion object {
        const val API_KEY = "c50f5aa4e7c95a2a553d29b81aad6dd0"
       // const val BASE_URL = "https://api.themoviedb.org/3/"

    }

}