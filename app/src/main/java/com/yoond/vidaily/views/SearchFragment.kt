package com.yoond.vidaily.views

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.marginLeft
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.SmallVideoListAdapter
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.databinding.FragmentSearchBinding
import com.yoond.vidaily.interfaces.OnVideoItemClickListener
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows the search results
 */
class SearchFragment : Fragment(), OnVideoItemClickListener, SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentSearchBinding
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var searchAdapter: SmallVideoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_search, menu)

        val search = menu.findItem(R.id.menu_search_search).actionView as SearchView
        search.setPadding(-16, 0, 0, 0)
        search.isIconified = false
        search.queryHint = resources.getString(R.string.menu_search)
        search.setOnQueryTextListener(this)
    }

    override fun onVideoItemClick(videoItem: VideoItem) {
        navigateToVideo(videoItem)
    }

    private fun init() {
        setHasOptionsMenu(true)
        setVisibility(false)

        searchAdapter = SmallVideoListAdapter((activity as MainActivity), requireContext(), this)
        binding.searchRecycler.adapter = searchAdapter
    }

    private fun navigateToVideo(videoItem: VideoItem) {
        findNavController().navigate(SearchFragmentDirections.actionNavSearchToNavVideo(videoItem))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        setVisibility(true)
        (activity as MainActivity).hideKeyboard()

        return if (query != null) {
            videoViewModel.getVideosByQuery(query).observe(viewLifecycleOwner) {
                searchAdapter.submitList(it)
            }
            true
        } else false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        setVisibility(false)
        return true
    }

    private fun setVisibility(isQuerySubmitted: Boolean) {
        if (isQuerySubmitted) {
            binding.searchLabel.visibility = View.GONE
            binding.searchRecycler.visibility = View.VISIBLE
        } else {
            binding.searchLabel.visibility = View.VISIBLE
            binding.searchRecycler.visibility = View.GONE
        }
    }
}