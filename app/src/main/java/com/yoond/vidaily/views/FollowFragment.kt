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
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.adapters.FollowListAdapter
import com.yoond.vidaily.databinding.FragmentFollowBinding
import com.yoond.vidaily.interfaces.OnProfileItemClickListener
import com.yoond.vidaily.viewmodels.UserViewModel

/**
 * Shows the user's following/followers list
 */
class FollowFragment : Fragment(), OnProfileItemClickListener {

    private lateinit var binding: FragmentFollowBinding
    private val args: FollowFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(true)
        (activity as MainActivity).setBackButtonVisible(true)
        (activity as MainActivity).setBottomNavVisible(false)
    }

    private fun init() {
        val followAdapter = FollowListAdapter((activity as MainActivity), requireContext(), this)
        binding.followRecycler.adapter = followAdapter

        subscribeUi(followAdapter)
    }

    /**
     * isFollowerList = true: follower 목록을 가져옴
     * isFollowerList = false: following 목록을 가져옴
     */
    private fun subscribeUi(followAdapter: FollowListAdapter) {
        val isFollowerList = args.isFollowerList

        if (isFollowerList) {
            userViewModel.getFollowers(args.uId).observe(viewLifecycleOwner) {
                followAdapter.submitList(it)
            }
        } else {
            userViewModel.getFollowings(args.uId).observe(viewLifecycleOwner) {
                followAdapter.submitList(it)
            }
        }
    }

    override fun onProfileItemClick(uId: String, profileUrl: String) {
        navigateToUser(uId, profileUrl)
    }

    private fun navigateToUser(uId: String, profileUrl: String) {
        findNavController().navigate(FollowFragmentDirections.actionNavFollowToNavUser(uId, profileUrl))
    }
}