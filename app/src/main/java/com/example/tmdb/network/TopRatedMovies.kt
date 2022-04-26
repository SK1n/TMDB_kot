package com.example.tmdb.network

import com.squareup.moshi.Json

data class TopRatedMovies(
    val id: String,
    @Json(name = "poster_path") val posterPath: String,
)
