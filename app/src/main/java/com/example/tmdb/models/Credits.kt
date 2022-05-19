package com.example.tmdb.models

import java.io.Serializable

data class CreditsModel(
    val cast: MutableList<CastModel>,
    val crew: List<CrewModel>,
    val id: Int
)

data class CastModel(
    val character: String,
    val id: Int,
    val name: String,
    val profile_path: String?
): Serializable

data class CrewModel(
    val adult: Boolean,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)
