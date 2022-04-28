package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.models.MoviesModel

class MoviesAdapter() :
    ListAdapter<MoviesModel, MoviesAdapter.MovieItemViewHolder>(DiffCallback) {

    var onItemClick: ((MoviesModel) -> Unit)? = null

    inner class MovieItemViewHolder(
        private var binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesItem: MoviesModel) {
            binding.movieItem = moviesItem
            binding.executePendingBindings()
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
            return oldItem.title == newItem.title
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieItemViewHolder {
        return MovieItemViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val topRated = getItem(position)
        holder.bind(topRated)
    }

}