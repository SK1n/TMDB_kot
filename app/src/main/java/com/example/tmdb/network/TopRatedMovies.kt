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
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "id") val id: Int,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "overview") val overview: String,
    @Json(name ="release_date") val releaseDate: String,
)
