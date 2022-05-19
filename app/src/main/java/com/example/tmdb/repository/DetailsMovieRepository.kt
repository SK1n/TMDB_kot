package com.example.tmdb.repository

import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.CreditsModel
import retrofit2.Response

object DetailsMovieRepository {
    suspend fun getCreditsPage(id: Int): Response<CreditsModel> {
        return RetrofitInstance.api.getCredits(id)
    }
}