package com.itis.android.di

import com.itis.android.domain.usecases.GetFilmUseCase
import com.itis.android.domain.usecases.GetFilmsUseCase
import com.itis.android.ui.screens.details.DetailsViewModel
import com.itis.android.ui.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    includes(repositoryModule)
    factory { GetFilmsUseCase(get()) }
    factory { GetFilmUseCase(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}
