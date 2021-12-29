package com.project.mymovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieDetails(

    val backdrop_path: String,
    val genres: List<Genre>,
    @PrimaryKey
    val id: Long,
    val original_title: String,
    val overview: String,

    val poster_path: String,
    val release_date: String,

    val runtime: Long,
    val status: String,

    val vote_average: Double,
    val vote_count: Long
)


