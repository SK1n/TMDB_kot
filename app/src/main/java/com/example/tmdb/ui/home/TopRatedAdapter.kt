package com.example.tmdb.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.MovieItemBinding
import com.example.tmdb.network.TopRatedMovies

class TopRatedAdapter() :
    ListAdapter<TopRatedMovies, TopRatedAdapter.TopRatedViewHolder>(DiffCallback) {

    var onItemClick: ((TopRatedMovies) -> Unit)? = null

    inner class TopRatedViewHolder(
        private var binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topRatedMovie: TopRatedMovies) {
            binding.topRatedMovieItem = topRatedMovie
            binding.executePendingBindings()
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
               // Log.d("TopRatedViewHolder", "pressed ${getItem((adapterPosition))}")
            }
        }
    }




    companion object DiffCallback : DiffUtil.ItemCallback<TopRatedMovies>() {
        override fun areItemsTheSame(oldItem: TopRatedMovies, newItem: TopRatedMovies): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TopRatedMovies, newItem: TopRatedMovies): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedViewHolder {

        return TopRatedViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context))

        )


    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val topRated = getItem(position)
        holder.bind(topRated)


    }

}