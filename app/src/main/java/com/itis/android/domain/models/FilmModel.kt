package com.itis.android.domain.models

open class FilmModel(
    val id: Int,
    val name: String,
    val poster: String,
    val description: String,
    val countries: List<String>,
    val genres: List<String>,
    val year: Int,
)
