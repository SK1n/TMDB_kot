package com.example.tmdb.ui.home

import com.squareup.moshi.Json

data class TopRatedMovies(
    @Json(name = "id") val id: Long,
    @Json(name = "title")val title: String,
    @Json(name = "poster_path") val posterPath: String,
)
