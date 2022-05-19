package com.example.tmdb.api

import com.example.tmdb.BuildConfig
import com.example.tmdb.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>

    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<TvShowsPageModel>

    @GET("tv/popular")
    suspend fun getTvPopular(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<TvShowsPageModel>

    @GET("tv/top_rated")
    suspend fun getTvTopRated(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<TvShowsPageModel>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path(value = "id") id: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<CreditsModel>

    @GET("tv/{tv_id}")
    suspend fun getSeasons(
        @Path(value = "tv_id") id: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<TvShowsDetails>

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonsDetail(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") seasonNumber: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<SeasonDetailModel>

    @GET("person/{id}")
    suspend fun getPerson(
        @Path(value = "id") id: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<PersonModel>

    @GET("person/{id}/movie_credits")
    suspend fun getPersonMovie(
        @Path(value = "id") id: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<PersonMovieModel>

    @GET("person/{id}/tv_credits")
    suspend fun getPersonTvShows(
        @Path(value = "id") id: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<PersonTvShowModel>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisode(
        @Path(value = "tv_id") id: Int,
        @Path(value = "season_number") seasonNumber: Int,
        @Path(value = "episode_number") episodeNumber: Int,
        @Query("api_key") api: String = BuildConfig.API_KEY,
    ): Response<EpisodeModel>
}
