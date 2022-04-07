package com.example.tmdb.services

import com.example.tmdb.models.Movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie")
    fun getMoviesList(
        @Query("api_key") apiKey: String ="d2477db2d5d6dfd2f939f8f439b246d9",
        @Query("page") page: Int
        ) : Call<List<Movies>>
}