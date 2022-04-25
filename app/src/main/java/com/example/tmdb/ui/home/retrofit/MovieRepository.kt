package com.example.tmdb.ui.home.retrofit

import android.util.Log
import com.example.tmdb.BuildConfig
import com.example.tmdb.R

import com.example.tmdb.api.GetMoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(
                BuildConfig.API_KEY
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }

    suspend fun getPopularMovies(movies: API,req: GetMoviesResponse): List<Movie>? {
       return movies.getPopularMovies(page = req.page).also { Log.d("response", "getPopularMovies: $it") }.body()
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                           onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
    }
}