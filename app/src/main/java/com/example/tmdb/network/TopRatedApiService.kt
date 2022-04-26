package com.example.tmdb.network


import com.example.tmdb.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    @GET("movie/popular")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page:  Int = 1
    ): List<TopRatedMovies>
}
/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TopRatedApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
