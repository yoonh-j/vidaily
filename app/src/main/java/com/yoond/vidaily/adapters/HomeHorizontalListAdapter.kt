package com.yoond.vidaily.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoond.vidaily.R
import com.yoond.vidaily.data.VideoMinimal
import com.yoond.vidaily.databinding.ItemHomeSmallBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener


class HomeHorizontalListAdapter(
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<VideoMinimal, RecyclerView.ViewHolder>(HomeTodayDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        HomeTodayViewHolder(
            ItemHomeSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        (holder as HomeTodayViewHolder).bind(video)
    }

    inner class HomeTodayViewHolder(
        private val binding: ItemHomeSmallBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                val item = binding.video
                if (item != null) {
                    onVideoItemClickListener.onItemClick(item.key)
                }
            }
        }

        fun bind(item: VideoMinimal) {
            binding.video = item
            Glide.with(context)
                .load(item.videoUrl)
                .placeholder(R.color.black)
                .into(binding.itemHomeThumbnail)
        }
    }
}

private class HomeTodayDiffCallback: DiffUtil.ItemCallback<VideoMinimal>() {
    override fun areItemsTheSame(oldItem: VideoMinimal, newItem: VideoMinimal) =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: VideoMinimal, newItem: VideoMinimal) =
        oldItem == newItem
}