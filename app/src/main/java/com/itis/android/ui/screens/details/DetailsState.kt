package com.itis.android.ui.screens.details

import com.itis.android.domain.models.FilmModel

data class DetailsState(
    val film: FilmModel? = null,
    val isLoading: Boolean = false,
)
