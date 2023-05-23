package com.itis.android.ui.screens.main

import com.itis.android.domain.models.FilmTopModel

sealed interface MainAction {

    class Navigate(val filmId: Int) : MainAction
}

sealed interface MainEvent {

    class OnFilmClick(val film: FilmTopModel) : MainEvent

    object LoadMore : MainEvent
}
