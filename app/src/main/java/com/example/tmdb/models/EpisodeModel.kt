package com.example.tmdb.models

data class EpisodeModel(
    val air_date: String,
    val crew: List<Crew>,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String? = "0",
    val season_number: Int,
    val still_path: String? = "",
    val vote_average: Double,
    val vote_count: Int
) {
    data class Crew(
        val credit_id: String,
        val department: String,
        val id: Int,
        val job: String,
        val name: String,
        val profile_path: String? = ""
    )

    data class GuestStar(
        val character: String,
        val credit_id: String,
        val id: Int,
        val name: String,
        val order: Int,
        val profile_path: String? = ""
    )
}