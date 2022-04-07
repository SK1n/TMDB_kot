package com.example.tmdb.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.squareup.picasso.Picasso
import com.example.tmdb.models.Movies

class MoviesAdapter(private val moviesList: List<Movies>) :RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return moviesList.size
    }
    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        Log.d("RESPONSE","List Count : ${moviesList.size} ")
        return holder.bind(moviesList[position])
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.movie_image)
        var movieName = itemView.findViewById<TextView>(R.id.movie_name)
        fun bind(movie: Movies){
            movieName.text = movie.title
            Picasso.get().load("https://image.tmdb.org/t/p/original/" + movie.poster_path).into(imageView)
            Log.d("path_img", "https://image.tmdb.org/t/p/original/" + movie.backdrop_path)
        }
    }

}