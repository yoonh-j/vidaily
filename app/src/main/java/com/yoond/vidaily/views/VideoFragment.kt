package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.amplifyframework.datastore.generated.model.User
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.CommentListAdapter
import com.yoond.vidaily.data.CommentItem
import com.yoond.vidaily.databinding.FragmentVideoBinding
import com.yoond.vidaily.interfaces.OnProfileItemClickListener
import com.yoond.vidaily.utils.FcmPushUtil
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows the video, uploaded user and comments
 */
class VideoFragment : Fragment(), OnProfileItemClickListener {
    private lateinit var binding: FragmentVideoBinding
    private val args: VideoFragmentArgs by navArgs()
    private val videoViewModel: VideoViewModel by viewModels()
    private var exoPlayer: ExoPlayer? = null
    private var comments: MutableList<CommentItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun init() {
        initPlayer()
        setMetadata()
        subscribeUi()

        binding.videoProfile.setOnClickListener {
            navigateToUser(args.videoItem.uid, args.videoItem.profileUrl)
        }
        binding.videoCommentDone.setOnClickListener {
            uploadComment()
            (activity as MainActivity).hideKeyboard()
        }
    }

    private fun subscribeUi() {
        videoViewModel.getComments(args.videoItem.id).observe(viewLifecycleOwner) { commentList ->
            commentList.sortByDescending { it.createdAt } // 작성 시간 내림차순 정렬
            comments = commentList
            setAdapter(comments)
        }
        videoViewModel.subscribeComments(args.videoItem.id).observe(viewLifecycleOwner) { comment ->
            comments.add(comment)
            comments.sortByDescending { it.createdAt }
            setAdapter(comments)
        }
    }

    private fun initPlayer() {
        val mediaItem = MediaItem.fromUri(args.videoItem.videoUrl)

        exoPlayer = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
    }

    private fun setMetadata() {
        binding.videoItem = args.videoItem
        binding.videoViews.text =
            resources.getString(R.string.video_views, args.videoItem.views)
        binding.videoCreatedAt.text =
            resources.getString(R.string.createdDate, args.videoItem.createdAt.toLong())

        Glide.with(this)
            .load(args.videoItem.profileUrl)
            .placeholder(R.color.black)
            .into(binding.videoProfile)
    }

    private fun setAdapter(comments: MutableList<CommentItem>) {
        val commentAdapter = CommentListAdapter(
            (activity as MainActivity),
            requireContext(),
            this
        )
        binding.videoRecycler.adapter = commentAdapter
        commentAdapter.submitList(comments)
    }

    private fun uploadComment() {
        val content = binding.videoCommentInput.text.toString()
        if (content == "") {
            Toast.makeText(requireContext(), resources.getString(R.string.toast_no_comment), Toast.LENGTH_LONG).show()
        } else {
            if (binding.videoItem != null) {
                val vId = binding.videoItem!!.id
                val createdAt = System.currentTimeMillis().toString()

                videoViewModel.uploadComment(content, createdAt, vId)
                binding.videoCommentInput.setText("")
                sendPush()
            }
        }
    }

    private fun sendPush() {
        val uId = args.videoItem.uid
        val title = resources.getString(R.string.app_name)
        val videoTitle = args.videoItem.title
        val message =
            if (videoTitle.length > 10) {
                resources.getString(R.string.alarm_comment, videoTitle.substring(0, 10) + "...")
            } else {
                resources.getString(R.string.alarm_comment, videoTitle)
            }

        FcmPushUtil.instance.sendPush(uId, title, message)
    }

    override fun onProfileItemClick(uId: String, profileUrl: String) {
        navigateToUser(uId, profileUrl)
    }

    private fun navigateToUser(uId: String, profileUrl: String) {
        findNavController().navigate(VideoFragmentDirections.actionNavVideoToNavUser(uId, profileUrl))
    }
}