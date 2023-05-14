package com.itis.android.ui.screens.main

import com.itis.android.domain.models.FilmTopModel

data class MainState(
    val isLoading: Boolean = false,
    val isLastPage: Boolean = false,
    val page: Int = 1,
    val showError: Boolean = false,
    val films: List<FilmTopModel> = emptyList(),
)
