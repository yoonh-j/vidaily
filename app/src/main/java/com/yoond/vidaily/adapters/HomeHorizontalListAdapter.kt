package com.yoond.vidaily.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.ItemHomeSmallBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener


class HomeHorizontalListAdapter(
    val activity: MainActivity,
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<VideoItem, RecyclerView.ViewHolder>(HomeTodayDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        HomeTodayViewHolder(
            ItemHomeSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as HomeTodayViewHolder).bind(item)
    }

    inner class HomeTodayViewHolder(
        private val binding: ItemHomeSmallBinding
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

            // get video url
            Amplify.Storage.getUrl("videos/${item.id}",
                { item.videoUrl = it.url.toString()
                    activity.runOnUiThread {
                        Glide.with(context)
                            .load(item.videoUrl)
                            .placeholder(R.color.black)
                            .into(binding.itemHomeThumbnail)
                    }

                    Log.i("VIDEO_LIST_ADAPTER", it.url.toString())
                },
                { Log.e("VIDEO_LIST_ADAPTER", "getVideoUrl failed", it) }
            )

            // get profile url
            Amplify.Storage.getUrl("profiles/${item.uid}",
                { item.profileUrl = it.url.toString() },
                { Log.e("VIDEO_LIST_ADAPTER", "getProfileUrl failed", it) }
            )
        }
    }
}

private class HomeTodayDiffCallback: DiffUtil.ItemCallback<VideoItem>() {
    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem == newItem
}