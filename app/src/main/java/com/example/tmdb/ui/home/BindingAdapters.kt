package com.example.tmdb.ui.home

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.tmdb.R
import com.example.tmdb.ui.home.TopRatedApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView,imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
        }
    }
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