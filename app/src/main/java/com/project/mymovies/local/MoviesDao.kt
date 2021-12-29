package com.project.mymovies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.mymovies.model.Genre
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.model.MoviesResult
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MoviesResult WHERE  (:genreId) IN (genre_ids)")
    fun getGenreMovies(genreId: Long): Flow<List<MoviesResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesList: List<MoviesResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenre(genre: List<Genre>)


    @Query("SELECT * FROM MoviesResult ")
    fun getMovies(): Flow<List<MoviesResult>>

    @Query("SELECT * FROM Genre")
    fun getMoviesGenre(): Flow<List<Genre>>

    @Query("SELECT * FROM MovieDetails WHERE id = :id")
    fun getMovieDetails(id: Long): Flow<MovieDetails>


    @Query("DELETE FROM MovieDetails  WHERE id = :id")
    suspend fun deleteMovieDetails(id: Long)

    @Query("DELETE FROM MoviesResult")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM Genre")
    suspend fun deleteAllMoviesGenre()


}