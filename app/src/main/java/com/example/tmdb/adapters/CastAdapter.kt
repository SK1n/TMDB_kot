package com.example.tmdb.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.CastItemBinding
import com.example.tmdb.models.CastModel


class CastAdapter : RecyclerView.Adapter<CastAdapter.CastItemViewHolder>() {
    var onItemClick: ((CastModel) -> Unit)? = null
        inner class CastItemViewHolder(private var binding: CastItemBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(castItem: CastModel?) {
                binding.castItem = castItem
                binding.executePendingBindings()
            }
            init {
                itemView.setOnClickListener {
                    onItemClick?.invoke(differ.currentList[adapterPosition])
                }
            }
        }

    private val differCallback = object  : DiffUtil.ItemCallback<CastModel>() {
        override fun areItemsTheSame(oldItem: CastModel, newItem: CastModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CastModel, newItem: CastModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastItemViewHolder {
        return CastItemViewHolder(
           CastItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    }

    override fun onBindViewHolder(holder: CastItemViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}