package com.example.tmdb.api


import androidx.paging.PagingData
import com.example.tmdb.BuildConfig
import com.example.tmdb.models.MoviesPage
import com.example.tmdb.models.TvShowModel
import com.example.tmdb.models.TvShowsPageModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BuildConfig.API_BASE_URL)
    .build()

/**
 * A public interface that exposes the [getTopRatedMovies] method
 */
interface ApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api:String = BuildConfig.API_KEY,
        @Query("page")page:Int = 1,
    ): Response<MoviesPage>
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api:String = BuildConfig.API_KEY,
        @Query("page")page:Int = 1,
    ): Response<MoviesPage>
    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(
        @Query("api_key") api:String = BuildConfig.API_KEY,
        @Query("page")page:Int = 1,
    ): Response<TvShowsPageModel>
    @GET("tv/popular")
    suspend fun getTvPopular(
        @Query("api_key") api:String = BuildConfig.API_KEY,
        @Query("page")page:Int = 1,
    ): Response<TvShowsPageModel>
    @GET("tv/top_rated")
    suspend fun getTvTopRated(
        @Query("api_key") api:String = BuildConfig.API_KEY,
        @Query("page")page:Int = 1,
    ): Response<TvShowsPageModel>

}