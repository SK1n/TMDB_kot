package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.CastItemBinding
import com.example.tmdb.databinding.GuestItemBinding
import com.example.tmdb.models.CastModel
import com.example.tmdb.models.EpisodeModel
import com.example.tmdb.models.SeasonDetailModel


class GuestAdapter : RecyclerView.Adapter<GuestAdapter.GuestItemViewHolder>() {
    var onItemClick: ((EpisodeModel.GuestStar) -> Unit)? = null

    inner class GuestItemViewHolder(private var binding: GuestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(castItem: EpisodeModel.GuestStar?) {
            binding.item = castItem
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<EpisodeModel.GuestStar>() {
        override fun areItemsTheSame(oldItem: EpisodeModel.GuestStar, newItem: EpisodeModel.GuestStar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeModel.GuestStar, newItem: EpisodeModel.GuestStar): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestItemViewHolder {
        return GuestItemViewHolder(
            GuestItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    }

    override fun onBindViewHolder(holder: GuestItemViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}