package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemPersonTvShowBinding
import com.example.tmdb.models.TvShowModel


class PersonTvShowAdapter : RecyclerView.Adapter<PersonTvShowAdapter.TvShowsViewHolder>() {
    var onItemClick: ((TvShowModel) -> Unit)? = null

    inner class TvShowsViewHolder(private var binding: ItemPersonTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShowModel?) {
            binding.tvShowsItem = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<TvShowModel>() {
        override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonTvShowAdapter.TvShowsViewHolder {
        return TvShowsViewHolder(
            ItemPersonTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}