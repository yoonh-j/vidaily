package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.amplifyframework.datastore.generated.model.Video
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.CommentListAdapter
import com.yoond.vidaily.databinding.FragmentVideoBinding
import com.yoond.vidaily.interfaces.OnProfileItemClickListener
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows the video, uploaded user and comments
 */
class VideoFragment : Fragment(), OnProfileItemClickListener {
    private lateinit var binding: FragmentVideoBinding
    private val args: VideoFragmentArgs by navArgs()
    private val videoViewModel: VideoViewModel by viewModels()
    private var exoPlayer: ExoPlayer? = null

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
        (activity as MainActivity).setBottomNavVisible(false)
        (activity as MainActivity).setBackButtonVisible(true)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
        (activity as MainActivity).setBackButtonVisible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun init() {
        initPlayer()

        val commentAdapter = CommentListAdapter(requireContext(), this)
        binding.videoRecycler.adapter = commentAdapter

        subscribeUi(commentAdapter)
    }

    private fun subscribeUi(commentAdapter: CommentListAdapter) {
        videoViewModel.getVideo(args.vId).observe(viewLifecycleOwner) { video ->
            setMetadata(video)
        }
        videoViewModel.getComments(args.vId).observe(viewLifecycleOwner) { commentList ->
            commentAdapter.submitList(commentList) {
                binding.videoRecycler.invalidateItemDecorations()
            }
        }
    }

    private fun initPlayer() {
        val mediaItem = MediaItem.fromUri(args.videoUrl)

        exoPlayer = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
    }

    private fun setMetadata(video: Video) {
        binding.video = video
        binding.videoViews.text =
            resources.getString(R.string.video_views, video.views)
        binding.videoCreatedAt.text =
            resources.getString(R.string.video_createdAt, video.createdAt.toLong())

        Glide.with(this)
            .load(args.profileUrl)
            .placeholder(R.color.black)
            .into(binding.videoProfile)
    }

    override fun onItemClick(pId: String) {
        Log.d("VIDEO_FRAGMENT", pId)
    }
}