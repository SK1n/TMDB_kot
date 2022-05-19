package com.example.tmdb.repository

import com.example.tmdb.api.RetrofitInstance
import com.example.tmdb.models.PersonModel
import com.example.tmdb.models.PersonMovieModel
import com.example.tmdb.models.PersonTvShowModel
import retrofit2.Response

object DetailsPersonRepository {
    suspend fun getMovies(id: Int): Response<PersonMovieModel> {
        return RetrofitInstance.api.getPersonMovie(id)
    }

    suspend fun getPersonPage(id: Int): Response<PersonModel> {
        return RetrofitInstance.api.getPerson(id)
    }

    suspend fun getTvShows(id: Int): Response<PersonTvShowModel> {
        return RetrofitInstance.api.getPersonTvShows(id)
    }

}