package com.example.tmdb.ui.home

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.R
import com.example.tmdb.network.TopRatedMovies



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
fun bindRecyclerView(recyclerView: RecyclerView, data: List<TopRatedMovies>?) {
    val adapter = recyclerView.adapter as TopRatedAdapter
    adapter.submitList(data)
}

@BindingAdapter("tmdbApiStatus")
fun bindStatus(statusImageView: ImageView, status: TopRatedApiStatus?) {
    when (status) {
        TopRatedApiStatus.LOADING-> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TopRatedApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TopRatedApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}