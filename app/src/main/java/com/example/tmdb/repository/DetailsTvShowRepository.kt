package com.example.tmdb.repository

import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.TvShowsDetails
import retrofit2.Response

object DetailsTvShowRepository {
    suspend fun getCreditsPage(id: Int): Response<TvShowsDetails> {
        return RetrofitInstance.api.getSeasons(
            id = id,
        )
    }
}