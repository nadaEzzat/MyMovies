package com.project.mymovies.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.mymovies.model.Genre
import com.project.mymovies.model.MovieDetails
import com.project.mymovies.model.MoviesResult

@Database(
    entities = [MoviesResult::class, Genre::class, MovieDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}