package com.yoond.vidaily.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Comment
import com.bumptech.glide.Glide
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.ItemCommentBinding
import com.yoond.vidaily.interfaces.OnProfileItemClickListener

class CommentListAdapter(
    val activity: MainActivity,
    val context: Context,
    val onProfileItemClickListener: OnProfileItemClickListener
): ListAdapter<Comment, RecyclerView.ViewHolder>(CommentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = getItem(position)
        (holder as CommentViewHolder).bind(comment)
    }

    inner class CommentViewHolder(
        private val binding: ItemCommentBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
           binding.itemCommentProfile.setOnClickListener {
               val item = binding.comment
               if (item != null) {
                   onProfileItemClickListener.onItemClick(item.uid)
               }
           }
        }
        fun bind(item: Comment) {
            binding.comment = item
            binding.itemCommentCreatedAt.text =
                context.resources.getString(R.string.createdTime, item.createdAt.toLong())


            // get profile url
            Amplify.Storage.getUrl("profiles/${item.uid}",
                { result ->
                    // user profile image
                    activity.runOnUiThread {
                        Glide.with(context)
                            .load(result.url.toString())
                            .placeholder(R.color.black)
                            .into(binding.itemCommentProfile)
                    }
                },
                { Log.e("VIDEO_REPOSITORY", "getVideoUrl failed", it) }
            )
        }
    }
}

private class CommentDiffCallback: DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
        oldItem == newItem
}