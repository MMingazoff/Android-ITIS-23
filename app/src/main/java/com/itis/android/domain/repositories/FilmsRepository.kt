package com.itis.android.domain.repositories

import com.itis.android.domain.models.FilmModel
import com.itis.android.domain.models.FilmTopModel

interface FilmsRepository {

    suspend fun getFilms(page: Int): List<FilmTopModel>

    suspend fun getFilm(id: Int): FilmModel
}
