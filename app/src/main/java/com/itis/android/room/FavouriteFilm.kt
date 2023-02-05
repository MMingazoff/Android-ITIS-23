package com.itis.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis.android.network.response.Country
import com.itis.android.network.response.Genre

@Entity(tableName = "favourite_films")
data class FavouriteFilm(
    @PrimaryKey
    val id: Int,
    val name: String,
    val poster: String,
    val description: String,
    @TypeConverters(CountryConverter::class)
    val countries: List<Country>,
    @TypeConverters(GenreConverter::class)
    val genres: List<Genre>,
    val year: Int?
)
