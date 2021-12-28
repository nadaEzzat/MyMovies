package com.project.mymovies.ui.details

import com.project.mymovies.network.APIInterface


class DetailsRepo constructor(private val apiInterface: APIInterface) {

    fun getMovieDetails(api_key: String, id: Long) = apiInterface.getMovieDetails(id, api_key)
    fun getGenre(api_key: String) = apiInterface.getGenre(api_key)
}