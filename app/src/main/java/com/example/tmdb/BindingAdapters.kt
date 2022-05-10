package com.example.tmdb

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    Log.d("MovieDetailsFragment", "binding: $url")
    Glide.with(imageView.context).load(url).error(R.drawable.ic_connection_error).into(imageView)
}