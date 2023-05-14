package com.itis.android.domain.usecases

import com.itis.android.domain.repositories.FilmsRepository

class GetFilmsUseCase(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke(page: Int) = runCatching { repository.getFilms(page) }
}
