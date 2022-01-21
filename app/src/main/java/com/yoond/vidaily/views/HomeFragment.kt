package com.yoond.vidaily.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.HomeHorizontalListAdapter
import com.yoond.vidaily.adapters.LargeVideoListAdapter
import com.yoond.vidaily.databinding.FragmentHomeBinding
import com.yoond.vidaily.decorators.ListDecoration
import com.yoond.vidaily.interfaces.OnVideoItemClickListener
import com.yoond.vidaily.viewmodels.UserViewModel
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows today's videos, popular videos, subscribing videos
 */
class HomeFragment : Fragment(), OnVideoItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val videoViewModel: VideoViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var pressedTimeInMillis: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setBackPressed()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(true)
        (activity as MainActivity).setBottomNavVisible(true)
    }

    private fun init() {
        val todayAdapter = HomeHorizontalListAdapter(requireContext(), this)
        binding.homeRecyclerToday.adapter = todayAdapter

        val popularAdapter = HomeHorizontalListAdapter(requireContext(), this)
        binding.homeRecyclerPopular.adapter = popularAdapter

        val followAdapter = LargeVideoListAdapter(requireContext(), this)
        binding.homeRecyclerFollow.adapter = followAdapter

        subscribeUi(todayAdapter, popularAdapter, followAdapter)

        val margin = resources.getDimension(R.dimen.item_video_small_margin).toInt()
        val decoration = ListDecoration(margin, false)

        binding.homeRecyclerToday.addItemDecoration(decoration)
        binding.homeRecyclerPopular.addItemDecoration(decoration)

        val largeMargin = resources.getDimension(R.dimen.item_video_large_margin).toInt()
        binding.homeRecyclerFollow.addItemDecoration(ListDecoration(largeMargin, true))

        binding.homeMoreToday.setOnClickListener {
        }
        binding.homeMorePopular.setOnClickListener {
        }
    }

    private fun subscribeUi(
        todayAdapter: HomeHorizontalListAdapter,
        popularAdapter: HomeHorizontalListAdapter,
        followAdapter: LargeVideoListAdapter
    ){
        videoViewModel.getVideosByDate().observe(viewLifecycleOwner) { videoList ->
            videoList.shuffle()  // 리스트 랜덤으로 섞음
            todayAdapter.submitList(videoList) {
                binding.homeRecyclerToday.invalidateItemDecorations()
            }
        }
        videoViewModel.getVideosByViews().observe(viewLifecycleOwner) { videoList ->
            videoList.sortByDescending { it.video.views } // 조회수 내림차순으로 정렬
            popularAdapter.submitList(videoList) {
                binding.homeRecyclerPopular.invalidateItemDecorations()
            }
        }
        if (Amplify.Auth.currentUser != null) {
            val uid = Amplify.Auth.currentUser.userId

            userViewModel.getUser(uid).observe(viewLifecycleOwner) { user ->
                videoViewModel.getVideosByFollowing(user.following).observe(viewLifecycleOwner) { metadataList ->
                    followAdapter.submitList(metadataList) {
                        binding.homeRecyclerFollow.invalidateItemDecorations()
                    }
                }
            }
        }
    }

    override fun onVideoItemClick(vId: String, videoUrl: String, profileUrl: String) {
        navigateToVideo(vId, videoUrl, profileUrl)
    }

    private fun navigateToVideo(vId: String, videoUrl: String, profileUrl: String) {
        findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavVideo(vId, videoUrl, profileUrl))
    }

    private fun setBackPressed() {
        if (System.currentTimeMillis() - pressedTimeInMillis > 2000) {
            pressedTimeInMillis = System.currentTimeMillis()
            Toast.makeText(context, resources.getString(R.string.home_toast_back_pressed), Toast.LENGTH_SHORT).show()
        } else {
            activity?.finish()
        }
    }
}