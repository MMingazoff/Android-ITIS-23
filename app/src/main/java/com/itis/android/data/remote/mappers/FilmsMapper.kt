package com.itis.android.data.remote.mappers

import com.itis.android.data.remote.datasource.responses.FilmResponse
import com.itis.android.data.remote.datasource.responses.FilmTopResponse
import com.itis.android.domain.models.FilmModel
import com.itis.android.domain.models.FilmTopModel

fun FilmResponse.toFilmModel() = FilmModel(
    name = name,
    poster = poster,
    description = description,
    countries = countries.map { it.country },
    genres = genres.map { it.genre },
    year = year
)

fun List<FilmTopResponse>.toFilmModels() = map {
    FilmTopModel(
        id = it.id,
        name = it.name,
        poster = it.poster,
        year = it.year,
    )
}
