package com.itis.android.data.remote.datasource.responses

import com.squareup.moshi.Json

class FilmResponse(
    @Json(name = "nameRu")
    val name: String,
    @Json(name = "posterUrl")
    val poster: String,
    val description: String,
    val countries: List<CountryResponse>,
    val genres: List<GenreResponse>,
    val year: Int
)

