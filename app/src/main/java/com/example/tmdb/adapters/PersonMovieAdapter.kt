package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemPersonMovieBinding
import com.example.tmdb.models.MoviesModel


class PersonMovieAdapter : RecyclerView.Adapter<PersonMovieAdapter.MovieItemViewHolder>() {
    var onItemClick: ((MoviesModel) -> Unit)? = null

    inner class MovieItemViewHolder(private var binding: ItemPersonMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MoviesModel?) {
            binding.movieItem = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonMovieAdapter.MovieItemViewHolder {
        return MovieItemViewHolder(
            ItemPersonMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}