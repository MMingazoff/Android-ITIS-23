package com.itis.android.data.repositories

import com.itis.android.data.remote.datasource.KinopoiskApi
import com.itis.android.data.remote.mappers.toFilmModel
import com.itis.android.data.remote.mappers.toFilmModels
import com.itis.android.domain.models.FilmModel
import com.itis.android.domain.models.FilmTopModel
import com.itis.android.domain.repositories.FilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmsRepositoryImpl(
    private val remote: KinopoiskApi
) : FilmsRepository {

    override suspend fun getFilms(page: Int): List<FilmTopModel> = withContext(Dispatchers.IO) {
        remote.getFilms(page).films.toFilmModels()
    }

    override suspend fun getFilm(id: Int): FilmModel = withContext(Dispatchers.IO) {
        remote.getFilmById(id).toFilmModel(id)
    }
}
