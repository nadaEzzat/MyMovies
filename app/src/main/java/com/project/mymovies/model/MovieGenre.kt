package com.project.mymovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieGenre(
    val genres: List<Genre>
)
@Entity(tableName = "Genre")
data class Genre(
    @PrimaryKey
    val id: Long,
    val name: String
)
