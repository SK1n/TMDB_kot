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

@BindingAdapter("topRatedMovies")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MoviesModel>?) {
    val adapter = recyclerView.adapter as MoviesAdapter
    adapter.submitList(data)
}

@BindingAdapter("tmdbApiStatus")
fun bindStatus(statusImageView: ImageView, status: MovieModelStatus?) {
    when (status) {
        MovieModelStatus.LOADING-> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MovieModelStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MovieModelStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}