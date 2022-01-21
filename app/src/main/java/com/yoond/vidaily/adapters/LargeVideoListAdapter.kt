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
import com.yoond.vidaily.databinding.ItemVideoLargeBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener

class LargeVideoListAdapter(
    val activity: MainActivity,
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<VideoItem, RecyclerView.ViewHolder>(LargeVideoDiffCallback()) {
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
                val item = binding.videoItem
                if (item != null) {
                    onVideoItemClickListener.onVideoItemClick(item)
                }
            }
        }

        fun bind(item: VideoItem) {
            binding.videoItem = item

            // get video url
            Amplify.Storage.getUrl("videos/${item.video.id}",
                { item.videoUrl = it.url.toString()

                    activity.runOnUiThread {
                        // video thumbnail
                        Glide.with(context)
                            .load(item.videoUrl)
                            .placeholder(R.color.black)
                            .into(binding.itemVideoLargeThumbnail)
                    }

                    Log.i("VIDEO_REPOSITORY", it.url.toString())
                },
                { Log.e("VIDEO_REPOSITORY", "getVideoUrl failed", it) }
            )

            // get profile url
            Amplify.Storage.getUrl("profiles/${item.video.uid}",
                {
                    item.profileUrl = it.url.toString()

                    activity.runOnUiThread {
                        // user profile image
                        Glide.with(context)
                            .load(item.profileUrl)
                            .placeholder(R.color.black)
                            .into(binding.itemVideoLargeProfile)
                    }
                },
                { Log.e("VIDEO_REPOSITORY", "getProfileUrl failed", it) }
            )
        }
    }
}

private class LargeVideoDiffCallback: DiffUtil.ItemCallback<VideoItem>() {
    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem) =
        oldItem.video.id == newItem.video.id

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem) =
        oldItem == newItem
}