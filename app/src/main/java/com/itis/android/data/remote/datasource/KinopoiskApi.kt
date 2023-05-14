package com.itis.android.data.remote.datasource

import com.itis.android.data.remote.datasource.responses.FilmResponse
import com.itis.android.data.remote.datasource.responses.FilmsPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("top")
    suspend fun getFilms(
        @Query("page") page: Int,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): FilmsPageResponse

    @GET("{film-id}")
    suspend fun getFilmById(
        @Path("film-id") filmId: Int
    ): FilmResponse
}
