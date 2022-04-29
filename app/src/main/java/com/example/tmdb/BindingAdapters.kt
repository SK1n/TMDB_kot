package com.example.tmdb

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.adapters.MoviesAdapter
import com.example.tmdb.models.MoviesModel

enum class MovieModelStatus { LOADING, ERROR, DONE }
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView,imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().build()
        Log.d("BindingAdapters", "bindImage: ${imgUri.toString()}")
        imgView.load("https://image.tmdb.org/t/p/original"
                +imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_error)
        }
    }
}