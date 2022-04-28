package com.example.tmdb.models

import com.squareup.moshi.Json
data class MoviesPage(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<MoviesModel>
)

data class MoviesModel(
    @Json(name = "title") val title: String,
    @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "overview") val overview: String,
    @Json(name = "release_date") val releaseDate: String,
)