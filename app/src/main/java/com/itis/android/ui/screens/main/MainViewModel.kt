package com.itis.android.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.itis.android.domain.usecases.GetFilmsUseCase
import com.itis.android.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
) : BaseViewModel<MainState, MainAction, MainEvent>(MainState()) {

    init {
        loadFilms()
    }

    override fun event(event: MainEvent) {
        when (event) {
            is MainEvent.OnFilmClick -> action { MainAction.Navigate(event.film.id) }
            MainEvent.LoadMore -> {
                if (!state.value.isLoading && !state.value.isLastPage && !state.value.showError) {
                    state { copy(page = page + 1) }
                    loadFilms()
                }
            }
        }
    }

    private fun loadFilms() {
        viewModelScope.launch {
            state { copy(isLoading = true) }
            getFilmsUseCase(state.value.page)
                .onSuccess {
                    if (it.isNotEmpty())
                        state {
                            copy(
                                films = films.toMutableList().apply { addAll(it) },
                                isLoading = false
                            )
                        }
                    else
                        state { copy(isLastPage = true) }
                }
                .onFailure {
                    state {
                        copy(
                            showError = true,
                            isLoading = false,
                            isLastPage = true
                        )
                    }
                }
        }
    }
}
