package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.SeasonItemBinding
import com.example.tmdb.models.SeasonModel


class SeasonsAdapter : RecyclerView.Adapter<SeasonsAdapter.SeasonsItemViewHolder>() {
    var onItemClick: ((SeasonModel) -> Unit)? = null

    inner class SeasonsItemViewHolder(private var binding: SeasonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeasonModel?) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<SeasonModel>() {
        override fun areItemsTheSame(oldItem: SeasonModel, newItem: SeasonModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeasonModel, newItem: SeasonModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsItemViewHolder {
        return SeasonsItemViewHolder(
            SeasonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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