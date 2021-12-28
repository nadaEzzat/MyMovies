package com.project.mymovies.model

data class MovieGenre(
    val genres: List<Genre>
)

data class Genre(
    val id: Long,
    val name: String
)
