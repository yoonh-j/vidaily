package com.yoond.vidaily.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoond.vidaily.R
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.ItemVideoSmallBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener

class SmallVideoListAdapter(
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<VideoItem, RecyclerView.ViewHolder>(SmallVideoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        VideoViewHolder(
            ItemVideoSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as VideoViewHolder).bind(item)
    }

    inner class VideoViewHolder(
        private val binding: ItemVideoSmallBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                val item = binding.videoItem
                if (item != null) {
                    onVideoItemClickListener.onVideoItemClick(item)
                }
            }
        }

        fun bind(item: VideoItem) {
            binding.videoItem = item

            Glide.with(context)
                .load(item.videoUrl)
                .placeholder(R.color.black)
                .into(binding.itemVideoSmallThumbnail)
        }
    }
}

private class SmallVideoDiffCallback: DiffUtil.ItemCallback<VideoItem>() {
    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem == newItem

}