package com.project.mymovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MoviesResult")
data class MoviesResult(
    @PrimaryKey
    val id: Long,
    val genre_ids: List<Long>,
    val original_title: String,
    val poster_path: String,
    val vote_average: Double
)


data class Movies(
    val page: Long,
    val results: List<MoviesResult>
)
