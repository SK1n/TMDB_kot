package com.example.tmdb.models

data class PersonTvShowModel(
    val cast: List<TvShowModel>,
    val id: Int
)