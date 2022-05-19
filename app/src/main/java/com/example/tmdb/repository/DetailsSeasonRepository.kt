package com.example.tmdb.repository

import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.SeasonDetailModel
import retrofit2.Response

object DetailsSeasonRepository {
    suspend fun getCreditsPage(id: Int, season: Int): Response<SeasonDetailModel> {
        return RetrofitInstance.api.getSeasonsDetail(
            id = id,
            seasonNumber = season,
        )
    }
}