package com.itis.android.network

import com.itis.android.network.response.Film
import com.itis.android.network.response.FilmsPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskService {
    @GET("top")
    fun getFilms(
        @Query("page") page: Int,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Call<FilmsPageResponse>

    @GET("{film-id}")
    fun getFilmById(
        @Path("film-id") filmId: Int
    ): Call<Film>
}
