package com.yoond.vidaily.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Metadata
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.HomeHorizontalListAdapter
import com.yoond.vidaily.adapters.LargeVideoListAdapter
import com.yoond.vidaily.databinding.FragmentHomeBinding
import com.yoond.vidaily.decorators.ListDecoration
import com.yoond.vidaily.interfaces.OnVideoItemClickListener
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows today's videos, popular videos, subscribing videos
 */
class HomeFragment : Fragment(), OnVideoItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private var metadataList = mutableListOf<Metadata>()
    private val videoViewModel: VideoViewModel by viewModels()
    private var pressedTimeInMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HOME_FRAGMENT", metadataList.toString())
    }

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
        val todayList = metadataList
        val popularList = todayList.sortedBy { it.views }.reversed()

        val todayAdapter = HomeHorizontalListAdapter(requireContext(), this)
//        todayAdapter.submitList(todayList)
        binding.homeRecyclerToday.adapter = todayAdapter

        val popularAdapter = HomeHorizontalListAdapter(requireContext(), this)
        popularAdapter.submitList(popularList)
        binding.homeRecyclerPopular.adapter = popularAdapter

        val followAdapter = LargeVideoListAdapter(requireContext(), this)
        followAdapter.submitList(todayList)
        binding.homeRecyclerFollow.adapter = followAdapter

        subscribeUi(todayAdapter, popularAdapter, followAdapter)

        val margin = resources.getDimension(R.dimen.video_small_margin).toInt()
        val decoration = ListDecoration(margin, false)

        binding.homeRecyclerToday.addItemDecoration(decoration)
        binding.homeRecyclerPopular.addItemDecoration(decoration)

        val largeMargin = resources.getDimension(R.dimen.video_large_margin).toInt()
        binding.homeRecyclerFollow.addItemDecoration(ListDecoration(largeMargin, true))

        binding.homeMoreToday.setOnClickListener {
//            Amplify.Auth.signOut(
//                { Log.i("AMPLIFY_SIGNOUT", "successed") },
//                { Log.e("AMPLIFY_SIGNOUT", "failed") }
//            )
        }
        binding.homeMorePopular.setOnClickListener {
        }
    }

    private fun subscribeUi(
        todayAdapter: HomeHorizontalListAdapter,
        popularAdapter: HomeHorizontalListAdapter,
        followAdapter: LargeVideoListAdapter
    ){
        videoViewModel.getAllMetaData().observe(viewLifecycleOwner) { metadataList ->
            todayAdapter.submitList(metadataList) {
                binding.homeRecyclerToday.invalidateItemDecorations()
            }
            popularAdapter.submitList(metadataList) {
                binding.homeRecyclerPopular.invalidateItemDecorations()
            }
            followAdapter.submitList(metadataList) {
                binding.homeRecyclerFollow.invalidateItemDecorations()
            }
        }
    }

    override fun onItemClick(key: String) {
    }

    private fun getMetadata() {
        Amplify.API.query(
            ModelQuery.list(Metadata::class.java),
            { response ->
                if (response.hasData()) {
                    response.data.forEach { metadata ->
                        metadataList.add(metadata)
                        Log.i("METADATA_LIST", metadata.title)
                    }
                }
            },
            { Log.e("METADATA_LIST", "failed: ", it) }
        )
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