package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.TvShowsItemBinding
import com.example.tmdb.models.TvShowModel

class TvShowsAdapter : PagingDataAdapter<TvShowModel, TvShowsAdapter.TvShowsItemViewHolder>(
    differCallback
) {
    var onItemClick: ((TvShowModel) -> Unit)? = null

    inner class TvShowsItemViewHolder(private var binding: TvShowsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShowModel?) {
            binding.tvShowsItem = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition)!!)
            }
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<TvShowModel>() {
            override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsItemViewHolder {
        return TvShowsItemViewHolder(
            TvShowsItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: TvShowsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}