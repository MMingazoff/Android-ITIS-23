package com.itis.android.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.itis.android.R
import com.itis.android.domain.models.FilmModel
import com.itis.android.ui.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    filmId: Int?,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    filmId?.let {
        viewModel.event(DetailsEvent.LoadFilm(it))
    }
    Surface(
        color = Theme.colors.primaryBackground,
        modifier = Modifier.fillMaxHeight()
    ) {
        state.value.film?.let {
            FilmDetails(it)
        }
    }
}

@Composable
fun FilmDetails(film: FilmModel) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = film.poster,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = Theme.shapes.cornersStyle)
                    .align(Alignment.Center)
            )
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = film.name,
                style = Theme.typography.heading,
                color = Theme.colors.primaryText,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = film.description,
                color = Theme.colors.primaryText,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.genres),
                    color = Theme.colors.primaryText,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = film.genres.joinToString(),
                    color = Theme.colors.primaryText,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.countries),
                    color = Theme.colors.primaryText,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = film.countries.joinToString(),
                    color = Theme.colors.primaryText,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = film.year.toString(),
                color = Theme.colors.primaryText,
            )
        }
    }
}
