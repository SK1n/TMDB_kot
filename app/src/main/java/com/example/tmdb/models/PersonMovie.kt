package com.example.tmdb.models

data class PersonMovieModel(
    val cast: List<MoviesModel>,
    val id: Int
)