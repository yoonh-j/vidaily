package com.yoond.vidaily.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoond.vidaily.data.CommentItem
import com.yoond.vidaily.databinding.ItemMyCommentBinding

class MyCommentListAdapter
    : ListAdapter<CommentItem, RecyclerView.ViewHolder>(MyCommentDiffCallback()) {

    companion object {
        val instance = MyCommentListAdapter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyCommentViewHolder(
            ItemMyCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MyCommentViewHolder).bind(item)
    }

    inner class MyCommentViewHolder(
        private val binding: ItemMyCommentBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentItem) {
            binding.commentItem = item
        }
    }
}

private class MyCommentDiffCallback: DiffUtil.ItemCallback<CommentItem>() {
    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem) =
        oldItem == newItem
}