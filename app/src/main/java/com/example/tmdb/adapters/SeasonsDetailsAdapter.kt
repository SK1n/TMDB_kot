package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemSeasonDetailsBinding
import com.example.tmdb.models.SeasonDetailModel


class SeasonsDetailsAdapter :
    RecyclerView.Adapter<SeasonsDetailsAdapter.SeasonDetailItemViewHolder>() {
    var onItemClick: ((SeasonDetailModel.Episode) -> Unit)? = null

    inner class SeasonDetailItemViewHolder(private var binding: ItemSeasonDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeasonDetailModel.Episode?) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<SeasonDetailModel.Episode>() {
        override fun areItemsTheSame(
            oldItem: SeasonDetailModel.Episode,
            newItem: SeasonDetailModel.Episode
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SeasonDetailModel.Episode,
            newItem: SeasonDetailModel.Episode
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonDetailItemViewHolder {
        return SeasonDetailItemViewHolder(
            ItemSeasonDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: SeasonDetailItemViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}