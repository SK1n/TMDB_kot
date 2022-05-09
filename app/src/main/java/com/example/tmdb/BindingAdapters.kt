package com.example.tmdb


import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView,imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().build()
        imgView.load("https://image.tmdb.org/t/p/original"
                +imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_error)
        }
    }
}