package com.yoond.vidaily.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Metadata
import com.amplifyframework.datastore.generated.model.User
import com.bumptech.glide.Glide
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.ItemVideoLargeBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener

class LargeVideoListAdapter(
    val context: Context,
    val onVideoItemClickListener: OnVideoItemClickListener
): ListAdapter<Metadata, RecyclerView.ViewHolder>(LargeVideoDiffCallback()) {
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
                val item = binding.metadata
                if (item != null) {
                    onVideoItemClickListener.onItemClick(item.id)
                }
            }
        }

        fun bind(item: Metadata) {
            binding.metadata = item
            // user profile image
//            Amplify.API.query(ModelQuery.get(User::class.java, item.uid),
//                { user ->
//                    Log.d("LARGE_VIDEO_LIST_ADAPTER", user.toString())
//                    if (user.hasData() && user.data != null) {
//                        Glide.with(context)
//                            .load(user.data.profileUrl)
//                            .placeholder(R.color.black)
//                            .into(binding.itemVideoLargeProfile)
//                    }
//                },
//                { Log.e("LARGE_VIDEO_LIST_ADAPTER", "user query failed: ", it) }
//            )
            // video thumbnail
            Glide.with(context)
                .load(item.url)
                .placeholder(R.color.black)
                .into(binding.itemVideoLargeThumbnail)
            Glide.with(context)
                .load(item.user.profileUrl)
                .placeholder(R.color.black)
                .into(binding.itemVideoLargeProfile)
            Log.d("LARGE_VIDEO_LIST_ADAPTER", item.user.profileUrl)
        }
    }
}

private class LargeVideoDiffCallback: DiffUtil.ItemCallback<Metadata>() {
    override fun areItemsTheSame(oldItem: Metadata, newItem: Metadata) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Metadata, newItem: Metadata) =
        oldItem == newItem
}