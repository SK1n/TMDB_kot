package com.example.tmdb

import android.graphics.Bitmap
import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition

@BindingAdapter("ImageUrl")
fun bindImageItem(cardView: CardView, url: String?) {
    Glide.with(cardView.context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions().centerCrop())
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_error)
        .into(object : BitmapImageViewTarget(cardView.findViewById(R.id.item_poster)) {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(bitmap, transition)
                Palette.from(bitmap).generate { palette ->
                    val color = palette!!.getVibrantColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.black_translucent_60
                        )
                    )

                    cardView.findViewById<View>(R.id.title_background).setBackgroundColor(color)
                }
            }
        })
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_error)
        .into(imageView)
}

@BindingAdapter("profileUrl")
fun bindProfileImage(imageView: ImageView, url: String?) {
    val options = RequestOptions()
        .centerCrop()
        .error(R.drawable.ic_error)

    Glide.with(imageView.context)
        .load(IMAGE_LOW_RES_BASE_URL.plus(url))
        .apply(options)
        .into(imageView)
}
@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, it: Any?) {
    view.visibility = if (it == "") View.GONE else View.VISIBLE
}

private const val IMAGE_LOW_RES_BASE_URL = "https://image.tmdb.org/t/p/w500"