package com.example.tmdb.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import retrofit2.http.Query
data class TopRatedMoviesPage(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<TopRatedMovies>
)
data class TopRatedMovies(
    @Json(name ="title") val title: String,
    @Json(name = "poster_path") val posterPath: String
)
