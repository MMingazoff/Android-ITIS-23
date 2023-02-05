package com.itis.android.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.android.R
import com.itis.android.databinding.ItemFilmBinding
import com.itis.android.network.Network
import com.itis.android.network.response.Film
import com.itis.android.network.response.FilmFromList
import com.itis.android.room.FavouriteFilm
import com.itis.android.room.FilmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class FilmHolder(
    private val binding: ItemFilmBinding,
    private val glide: RequestManager,
    private val repository: FilmsRepository?,
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(film: FilmFromList) {
        binding.run {
            titleTv.text = film.name
            authorTv.text = film.year
            ivFavourite.isVisible = film.isFavourite
            glide
                .load(film.poster)
                .placeholder(R.drawable.loading)
                .into(posterIv)
            root.setOnClickListener {
                action(film.id)
            }
            root.setOnLongClickListener {
                if (film.isFavourite) {
                    ivFavourite.visibility = View.GONE
                    coroutineScope.launch(Dispatchers.IO) {
                        repository?.deleteById(film.id)
                    }
                } else {
                    getFilmInfo(film.id)
                    ivFavourite.visibility = View.VISIBLE
                }
                film.isFavourite = !film.isFavourite
                true
            }
        }
    }

    private fun getFilmInfo(filmId: Int) {
        Network.kinopoiskService.getFilmById(filmId).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (!response.isSuccessful)
                    return
                val film = response.body()!!
                val favouriteFilm = FavouriteFilm(
                    id = filmId,
                    name = film.name,
                    poster = film.poster,
                    description = film.description,
                    countries = film.countries,
                    genres = film.genres,
                    year = film.year
                )
                coroutineScope.launch(Dispatchers.IO) {
                    repository?.add(favouriteFilm)
                    val bitmap = async(Dispatchers.IO) {
                        glide
                            .asBitmap()
                            .load(favouriteFilm.poster)
                            .submit()
                            .get()
                    }
                    saveImageToInternalStorage(
                        context,
                        bitmap.await(),
                        filmId
                    )
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                Log.i("error", t.message.toString())
            }

        })
    }

    private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, filmId: Int) {
        val fileName = "$filmId.png"
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        try {
            val stream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: Exception) {
            Log.e("Image saving error", e.toString())
        }
    }
}
