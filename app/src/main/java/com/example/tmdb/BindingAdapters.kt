package com.example.tmdb


import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import androidx.palette.graphics.Palette

@BindingAdapter("ImageUrl")
fun bindImageItem(cardView: CardView, url: String?) {
    Glide.with(cardView.context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(object : BitmapImageViewTarget(cardView.findViewById(R.id.item_poster)) {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(bitmap, transition)
                Palette.from(bitmap).generate { palette ->
                    val color = palette!!.getVibrantColor(
                        ContextCompat.getColor(cardView.context,
                            R.color.black_translucent_60))

                    cardView.findViewById<View>(R.id.title_background).setBackgroundColor(color)
                }
            }
        })
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).error(R.drawable.ic_connection_error).into(imageView)
}