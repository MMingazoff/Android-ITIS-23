package com.itis.android.data.remote.datasource.responses

import com.squareup.moshi.Json

class FilmTopResponse(
    @Json(name = "filmId")
    val id: Int,
    @Json(name = "nameRu")
    val name: String,
    @Json(name = "posterUrl")
    val poster: String,
    val year: String,
)
