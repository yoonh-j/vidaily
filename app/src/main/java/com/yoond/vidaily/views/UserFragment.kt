package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.bumptech.glide.Glide
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.SmallVideoListAdapter
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.FragmentUserBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener
import com.yoond.vidaily.viewmodels.UserViewModel
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows the user's videos, followings and followers
 */
class UserFragment : Fragment(), OnVideoItemClickListener {
    private lateinit var binding: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by viewModels()
    private val videoViewModel: VideoViewModel by viewModels()
    private val curUId = Amplify.Auth.currentUser.userId // 현재 사용자

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(true)
        (activity as MainActivity).setBackButtonVisible(true)
        (activity as MainActivity).setBottomNavVisible(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).setBackButtonVisible(false)
    }

    private fun init() {
        val videoAdapter = SmallVideoListAdapter((activity as MainActivity), requireContext(), this)
        binding.userRecycler.adapter = videoAdapter

        subscribeUi(videoAdapter)
        setOnFollowClick()

        Glide.with(this)
            .load(args.profileUrl)
            .placeholder(R.color.black)
            .into(binding.userProfile)
    }

    private fun subscribeUi(videoAdapter: SmallVideoListAdapter) {
        userViewModel.getUser(args.uId).observe(viewLifecycleOwner) { user ->
            binding.user = user
            setFollowBtn(user)
        }
        videoViewModel.getVideosByUser(args.uId).observe(viewLifecycleOwner) { videos ->
            videos.forEach {
                it.profileUrl = args.profileUrl
            }
            videoAdapter.submitList(videos)
        }
    }

    /**
     * @param user: 화면에 표시된 사용자
     */
    private fun setFollowBtn(user: User) {
        Log.d("USER_FRAGMENT", "curUser: ${curUId}, user: ${user.id}")
        if (curUId == user.id) {
            binding.userBtn.visibility = View.GONE
        } else {
            binding.userBtn.visibility = View.VISIBLE
            binding.userBtn.text =
                // 화면의 사용자 팔로워에 현재 사용자가 포함되어 있으면 버튼에 "팔로잉" 표시
                // 팔로우 하지 않았으면 "팔로우" 표시
                if (user.follower.contains(curUId)) {
                    resources.getString(R.string.user_btn_following)
                } else {
                    resources.getString(R.string.user_btn_follow)
                }
        }
        binding.userBtn.setOnClickListener {
            // 클릭 시점에 버튼 텍스트가 "팔로우"면 "팔로잉"으로 변경(팔로우)
            // "팔로잉"이면 "팔로우"로 변경(언팔로우)
            if (binding.userBtn.text == resources.getString(R.string.user_btn_follow)) {
                binding.userBtn.text = resources.getString(R.string.user_btn_following)
                userViewModel.createFollower(user.id).observe(viewLifecycleOwner) {
                    if (it) {
                        val followers = binding.userFollower.text.toString().toInt()
                        binding.userFollower.text = (followers + 1).toString()
                    }
                }
            } else {
                binding.userBtn.text = resources.getString(R.string.user_btn_follow)
                userViewModel.deleteFollower(user.id).observe(viewLifecycleOwner) {
                    if (it) {
                        val followers = binding.userFollower.text.toString().toInt()
                        binding.userFollower.text = (followers - 1).toString()
                    }
                }
            }
        }
    }

    private fun setOnFollowClick() {
        binding.userFollower.setOnClickListener {
            if (binding.user != null) {
                navigateToFollow(binding.user!!.id, true)
            }
        }
        binding.userFollowing.setOnClickListener {
            if (binding.user != null) {
                navigateToFollow(binding.user!!.id, false)
            }
        }
    }

    override fun onVideoItemClick(videoItem: VideoItem) {
        navigateToVideo(videoItem)
    }

    private fun navigateToVideo(videoItem: VideoItem) {
        findNavController().navigate(UserFragmentDirections.actionNavUserToNavVideo(videoItem))
    }

    private fun navigateToFollow(uId: String, isFollowerList: Boolean) {
        findNavController().navigate(UserFragmentDirections.actionNavUserToNavFollow(uId, isFollowerList))
    }
}