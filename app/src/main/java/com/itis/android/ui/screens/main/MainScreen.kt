package com.itis.android.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.itis.android.domain.models.FilmTopModel
import com.itis.android.ui.Screen
import com.itis.android.ui.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    FilmsList(state = state.value, eventHandler = viewModel::event)

    MainScreenActions(
        navController = navController,
        action = action
    )
}

@Composable
fun FilmsList(
    state: MainState,
    eventHandler: (MainEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(state.films, key = { it.id }) { film ->
            Film(film = film, onClick = { eventHandler(MainEvent.OnFilmClick(film)) })
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (!state.isLastPage) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Theme.colors.primaryText
                    )
                    eventHandler(MainEvent.LoadMore)
                }
            }
        }
    }
}

@Composable
fun Film(
    film: FilmTopModel,
    onClick: (FilmTopModel) -> Unit
) {
    Row(modifier = Modifier
        .height(80.dp)
        .clickable {
            onClick(film)
        }
    ) {
        AsyncImage(
            model = cachingImage(url = film.poster),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = film.name,
                color = Theme.colors.primaryText,
                style = Theme.typography.body
            )
            Text(
                text = film.year,
                color = Theme.colors.primaryText,
                style = Theme.typography.caption
            )
        }
    }
}

@Composable
fun MainScreenActions(
    navController: NavController,
    action: MainAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            is MainAction.Navigate -> {
                navController.navigate(Screen.Details(filmId = action.filmId).processedRoute)
            }

            null -> Unit
        }
    }
}

@Composable
fun cachingImage(url: String) = ImageRequest.Builder(LocalContext.current)
    .data(url)
    .crossfade(true)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .build()
