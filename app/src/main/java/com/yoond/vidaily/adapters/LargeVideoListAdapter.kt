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
import com.yoond.vidaily.databinding.ItemVideoLargeBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener

class LargeVideoListAdapter(
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<VideoMinimal, RecyclerView.ViewHolder>(LargeVideoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewHolder(
            ItemVideoLargeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        (holder as VideoViewHolder).bind(video)
    }

    inner class VideoViewHolder(
        private val binding: ItemVideoLargeBinding
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
            // video thumbnail
            Glide.with(context)
                .load(item.videoUrl)
                .placeholder(R.color.black)
                .into(binding.itemVideoLargeThumbnail)
            // user profile image
            Glide.with(context)
                .load(item.profileUrl)
                .placeholder(R.color.black)
                .into(binding.itemVideoLargeProfile)
        }
    }
}

private class LargeVideoDiffCallback: DiffUtil.ItemCallback<VideoMinimal>() {
    override fun areItemsTheSame(oldItem: VideoMinimal, newItem: VideoMinimal) =
        oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: VideoMinimal, newItem: VideoMinimal) =
        oldItem == newItem
}