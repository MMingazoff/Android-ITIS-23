package com.itis.android.data.remote.datasource.responses

import com.squareup.moshi.Json

class FilmsPageResponse(
    @Json(name = "pagesCount")
    val pagesCount: Int,
    val films: List<FilmTopResponse>,
)
