package com.example.tmdb.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.models.MoviesModel

class MoviesAdapter : PagingDataAdapter<MoviesModel, MoviesAdapter.MovieItemViewHolder>(
    differCallback
) {
    var onItemClick: ((MoviesModel) -> Unit)? = null

    inner class MovieItemViewHolder(private var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesModel?) {
            binding.movieItem = movie
            binding.executePendingBindings()
        }

//        init {
//            itemView.setOnClickListener {
//                onItemClick?.invoke(getItem(bindingAdapterPosition)!!)
//            }
//        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
            override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{onItemClick?.invoke(getItem(position)!!)}
    }
}