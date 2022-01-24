package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.adapters.SmallVideoListAdapter
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.FragmentHomeListBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener


/**
 * shows a list of videos
 */
class HomeListFragment : Fragment(), OnVideoItemClickListener {
    private lateinit var binding: FragmentHomeListBinding
    private val args: HomeListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        init()
        return binding.root
    }

    private fun init() {
        val items = args.videoItems
        val adapter = SmallVideoListAdapter((activity as MainActivity), requireContext(), this)
        binding.homeListRecycler.adapter = adapter
        adapter.submitList(items.toList())
    }

    override fun onVideoItemClick(videoItem: VideoItem) {
        navigateToVideo(videoItem)
    }

    private fun navigateToVideo(videoItem: VideoItem) {
        findNavController().navigate(HomeListFragmentDirections.actionNavHomeListToNavVideo(videoItem))
    }
}