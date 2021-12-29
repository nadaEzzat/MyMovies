package com.project.mymovies.ui.details

import androidx.room.withTransaction
import com.project.mymovies.local.MoviesDataBase
import com.project.mymovies.network.APIInterface
import com.project.mymovies.utilites.networkBoundResource
import javax.inject.Inject


class DetailsRepo @Inject constructor(
    private val api: APIInterface,
    private val db: MoviesDataBase
) {
    private val moviesDao = db.moviesDao()

    fun getMovieDetails(id: Long) = networkBoundResource(
        query = {
            moviesDao.getMovieDetails(id)
        },
        fetch = {
            api.getMovieDetails(id, APIInterface.API_KEY)
        },
        saveFetchResult = { movies ->
            db.withTransaction {
                moviesDao.deleteMovieDetails(id)
                moviesDao.insertMovieDetails(movies)
            }
        }
    )
}