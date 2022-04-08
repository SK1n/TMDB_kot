package com.example.tmdb.ui.home

import com.example.tmdb.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}