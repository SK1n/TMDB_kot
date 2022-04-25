package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.tmdb.R
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.ui.home.retrofit.Movie

class MoviesAdapter(
    private var movies: List<Movie>
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(MoviesDiffCallback()) {

    var onItemClick: ((Movie) -> Unit)? = null;
class MoviesDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)

        val binding = MovieItemBinding.inflate(view)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies

        notifyDataSetChanged()

    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :RecyclerView.ViewHolder(binding.root) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            binding.movieOverview.text = movie.overview
            binding.movieRating.text = movie.rating.toString()
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(FitCenter())
                .into(poster)
        }
        init {
            itemView.setOnClickListener { onItemClick?.invoke(movies[adapterPosition]) }
        }
    }
}