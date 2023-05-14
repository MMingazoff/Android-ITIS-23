package com.itis.android.ui.screens.details

sealed interface DetailAction

sealed interface DetailsEvent {

    class LoadFilm(val filmId: Int) : DetailsEvent
}
