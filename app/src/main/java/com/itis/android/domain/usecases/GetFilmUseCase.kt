package com.itis.android.domain.usecases

import com.itis.android.domain.repositories.FilmsRepository

class GetFilmUseCase(
    private val filmsRepository: FilmsRepository,
) {

    suspend operator fun invoke(id: Int) = runCatching {
        filmsRepository.getFilm(id)
    }
}
