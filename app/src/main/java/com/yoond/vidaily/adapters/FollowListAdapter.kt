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
import com.yoond.vidaily.data.UserItem
import com.yoond.vidaily.databinding.ItemFollowBinding
import com.yoond.vidaily.interfaces.OnProfileItemClickListener

class FollowListAdapter(
    val activity: MainActivity,
    val context: Context,
    val onProfileItemClickListener: OnProfileItemClickListener
): ListAdapter<UserItem, RecyclerView.ViewHolder>(FollowDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FollowViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as FollowViewHolder).onBind(item)
    }

    inner class FollowViewHolder(
        private val binding: ItemFollowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                val item = binding.userItem
                if (item != null) {
                    onProfileItemClickListener.onProfileItemClick(item.id, item.profileUrl)
                }
            }
        }

        fun onBind(item: UserItem) {
            binding.userItem = item

            // get profile url
            Amplify.Storage.getUrl("profiles/${item.id}",
                {
                    item.profileUrl = it.url.toString()

                    activity.runOnUiThread {
                        Glide.with(context)
                            .load(item.profileUrl)
                            .placeholder(R.color.black)
                            .into(binding.followProfile)
                    }
                },
                { Log.e("FOLLOW_LIST_ADAPTER", "getProfileUrl failed", it) }
            )
        }
    }
}

private class FollowDiffCallback: DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem.username == newItem.username


    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem == newItem
}