package com.project.mymovies.ui.home

import androidx.room.withTransaction
import com.project.mymovies.local.MoviesDataBase
import com.project.mymovies.network.APIInterface
import com.project.mymovies.network.APIInterface.Companion.API_KEY
import com.project.mymovies.utilites.networkBoundResource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: APIInterface,
    private val db: MoviesDataBase
) {
    private val moviesDao = db.moviesDao()

    fun getMovies() = networkBoundResource(
        query = {
            moviesDao.getMovies()
        },
        fetch = {
            api.getAllMovies(API_KEY, 1)
        },
        saveFetchResult = { movies ->
            db.withTransaction {
                moviesDao.deleteAllMovies()
                moviesDao.insertAll(movies.results)
            }
        }
    )

    fun getGenre() = networkBoundResource(
        query = {
            moviesDao.getMoviesGenre()
        },
        fetch = {
            api.getGenre(API_KEY)
        },
        saveFetchResult = { it ->
            db.withTransaction {
                moviesDao.deleteAllMoviesGenre()
                moviesDao.insertMovieGenre(it.genres)
            }
        }
    )

    fun getGenreMovies(id: Long) = networkBoundResource(
        query = {
            moviesDao.getGenreMovies(id)
        },
        fetch = {

        },
        saveFetchResult = {
        }
    )
}
