package com.itis.android.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.itis.android.domain.usecases.GetFilmUseCase
import com.itis.android.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getFilmUseCase: GetFilmUseCase,
) : BaseViewModel<DetailsState, DetailAction, DetailsEvent>(DetailsState()) {

    override fun event(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.LoadFilm -> {
                if (state.value.film == null)
                    loadFilm(event.filmId)
            }
        }
    }

    private fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            getFilmUseCase(filmId)
                .onSuccess { state { copy(film = it, isLoading = false) } }
        }
    }
}
