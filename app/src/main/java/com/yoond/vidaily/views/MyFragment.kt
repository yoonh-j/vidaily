package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.SmallVideoListAdapter
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.FragmentMyBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener
import com.yoond.vidaily.viewmodels.AuthViewModel
import com.yoond.vidaily.viewmodels.UserViewModel
import com.yoond.vidaily.viewmodels.VideoViewModel

class MyFragment : Fragment(), OnVideoItemClickListener {
    private lateinit var binding: FragmentMyBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val videoViewModel: VideoViewModel by viewModels()
    private var profileUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val videoAdapter = SmallVideoListAdapter((activity as MainActivity), requireContext(), this)
        binding.myRecycler.adapter = videoAdapter

        subscribeUi(videoAdapter)
        setLogoutBtn()
        setOnFollowClick()
    }

    private fun subscribeUi(videoAdapter: SmallVideoListAdapter) {
        val uId = Amplify.Auth.currentUser.userId

        userViewModel.getProfileUrl(uId).observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .placeholder(R.color.black)
                .into(binding.myProfile)
            profileUrl = it
        }
        userViewModel.getUser(uId).observe(viewLifecycleOwner) {
            binding.user = it
        }
        videoViewModel.getVideosByUser(uId).observe(viewLifecycleOwner) { videos ->
            videos.forEach {
                if (profileUrl != "") {
                    it.profileUrl = profileUrl
                }
            }
            videoAdapter.submitList(videos)
        }
    }

    private fun setLogoutBtn() {
        binding.myBtn.setOnClickListener {
            authViewModel.logout()
            navigateToLogin()
        }
    }

    private fun setOnFollowClick() {
        binding.myFollower.setOnClickListener {
            if (binding.user != null) {
                navigateToFollow(binding.user!!.id, true)
            }
        }
        binding.myFollowing.setOnClickListener {
            if (binding.user != null) {
                navigateToFollow(binding.user!!.id, false)
            }
        }
    }

    override fun onVideoItemClick(videoItem: VideoItem) {
        navigateToVideo(videoItem)
    }

    private fun navigateToLogin() {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavLogin())
    }

    private fun navigateToFollow(uId: String, isFollowerList: Boolean) {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavFollow(uId, isFollowerList))
    }

    private fun navigateToVideo(videoItem: VideoItem) {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavVideo(videoItem))
    }
}