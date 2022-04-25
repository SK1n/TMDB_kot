package com.example.tmdb.ui.home.retrofit


import com.example.tmdb.Constants
import com.example.tmdb.ui.home.retrofit.GetMoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovies {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page:  Int = 1
    ): Response<List<Movie>>
}