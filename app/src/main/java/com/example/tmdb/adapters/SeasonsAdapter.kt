package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemSeasonsBinding
import com.example.tmdb.models.TvShowsDetails.Season


class SeasonsAdapter : RecyclerView.Adapter<SeasonsAdapter.SeasonsItemViewHolder>() {
    var onItemClick: ((Season) -> Unit)? = null

    inner class SeasonsItemViewHolder(private var binding: ItemSeasonsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Season?) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Season>() {
        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsItemViewHolder {
        return SeasonsItemViewHolder(
            ItemSeasonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: SeasonsItemViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}