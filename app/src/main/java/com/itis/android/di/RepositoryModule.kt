package com.itis.android.di

import com.itis.android.data.repositories.FilmsRepositoryImpl
import com.itis.android.domain.repositories.FilmsRepository
import org.koin.dsl.module

val repositoryModule = module {
    includes(networkModule)
    single<FilmsRepository> { FilmsRepositoryImpl(get()) }
}
