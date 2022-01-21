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
                    onVideoItemClickListener.onVideoItemClick(
                        item.video.id,
                        item.videoUrl,
                        item.profileUrl
                    )
                }
            }
        }

        fun bind(item: VideoItem) {
            Log.d("HOME_ADAPTER", "onBind: video ${item.videoUrl}")
            binding.videoItem = item

            // get video url
            Amplify.Storage.getUrl("videos/${item.video.id}",
                { item.videoUrl = it.url.toString()
                    activity.runOnUiThread {
                        Glide.with(context)
                            .load(item.videoUrl)
                            .placeholder(R.color.black)
                            .into(binding.itemHomeThumbnail)
                    }

                    Log.i("VIDEO_REPOSITORY", it.url.toString())
                },
                { Log.e("VIDEO_REPOSITORY", "getVideoUrl failed", it) }
            )

            // get profile url
            Amplify.Storage.getUrl("profiles/${item.video.uid}",
                { item.profileUrl = it.url.toString() },
                { Log.e("VIDEO_REPOSITORY", "getProfileUrl failed", it) }
            )
        }
    }
}

private class HomeTodayDiffCallback: DiffUtil.ItemCallback<VideoItem>() {
    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem.video.id == oldItem.video.id

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem == newItem
}