package com.example.tmdb.models

import java.io.Serializable

data class TvShowsPageModel(
    val page: Int,
    val results: MutableList<TvShowModel>,
    val total_pages: Int,
    val total_results: Int
)

data class TvShowModel(
    val backdrop_path: String? = "",
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
) : Serializable