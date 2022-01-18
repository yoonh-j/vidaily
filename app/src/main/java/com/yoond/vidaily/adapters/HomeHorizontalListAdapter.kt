package com.yoond.vidaily.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Metadata
import com.bumptech.glide.Glide
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.ItemHomeSmallBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener


class HomeHorizontalListAdapter(
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<Metadata, RecyclerView.ViewHolder>(HomeTodayDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        HomeTodayViewHolder(
            ItemHomeSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val metadata = getItem(position)
        (holder as HomeTodayViewHolder).bind(metadata)
    }

    inner class HomeTodayViewHolder(
        private val binding: ItemHomeSmallBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                val item = binding.metadata
                if (item != null) {
                    onVideoItemClickListener.onItemClick(item.id)
                }
            }
        }

        fun bind(item: Metadata) {
            binding.metadata = item
            Glide.with(context)
                .load(item.url)
                .placeholder(R.color.black)
                .into(binding.itemHomeThumbnail)
        }
    }
}

private class HomeTodayDiffCallback: DiffUtil.ItemCallback<Metadata>() {
    override fun areItemsTheSame(oldItem: Metadata, newItem: Metadata) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Metadata, newItem: Metadata) =
        oldItem == newItem
}