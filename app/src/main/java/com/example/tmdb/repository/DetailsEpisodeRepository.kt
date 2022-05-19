package com.example.tmdb.repository

import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.EpisodeModel
import retrofit2.Response

object DetailsEpisodeRepository {
    suspend fun getCreditsPage(id: Int, season: Int, episode: Int): Response<EpisodeModel> {
        return RetrofitInstance.api.getEpisode(
            id = id,
            seasonNumber = season,
            episodeNumber = episode,
        )
    }
}