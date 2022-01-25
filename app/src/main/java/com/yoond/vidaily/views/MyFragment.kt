package com.yoond.vidaily.views

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import com.yoond.vidaily.utils.SHARED_PREFS_PROFILE_URL
import com.yoond.vidaily.viewmodels.AuthViewModel
import com.yoond.vidaily.viewmodels.UserViewModel
import com.yoond.vidaily.viewmodels.VideoViewModel

class MyFragment : Fragment(), OnVideoItemClickListener {
    private lateinit var binding: FragmentMyBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences
    private var profileUrl = ""
    private var selectedUri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_my, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_my_preference -> {
                navigateToPreference()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun init() {
        setHasOptionsMenu(true)
        sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)!!

        val videoAdapter = SmallVideoListAdapter((activity as MainActivity), requireContext(), this)
        binding.myRecycler.adapter = videoAdapter

        subscribeUi(videoAdapter)
        setOnFollowClick()
    }

    private fun subscribeUi(videoAdapter: SmallVideoListAdapter) {
        val uId = Amplify.Auth.currentUser.userId

        userViewModel.getUser(uId).observe(viewLifecycleOwner) {
            binding.user = it
        }

        userViewModel.getProfileUrl(uId).observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .placeholder(R.color.black)
                .into(binding.myProfile)
            profileUrl = it
            updateProfileUrlInSharedPrefs(it)
        }

        /**
         * videoFragment로 전환했을 때 프로필 사진을 표시하기 위해 각 videoItem의 profileUrl 설정
         */
        videoViewModel.getVideosByUser(uId).observe(viewLifecycleOwner) { videos ->
            videos.forEach {
                if (profileUrl != "") {
                    it.profileUrl = profileUrl
                }
            }
            videoAdapter.submitList(videos)
        }
    }

    private fun updateProfileUrlInSharedPrefs(url: String) {
        sharedPrefs.edit()
            .putString(SHARED_PREFS_PROFILE_URL, url)
            .apply()
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

    private fun navigateToFollow(uId: String, isFollowerList: Boolean) {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavFollow(uId, isFollowerList))
    }

    private fun navigateToPreference() {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavPreference())
    }

    private fun navigateToVideo(videoItem: VideoItem) {
        findNavController().navigate(MyFragmentDirections.actionNavMyToNavVideo(videoItem))
    }
}