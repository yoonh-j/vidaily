package com.yoond.vidaily.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.adapters.HomeHorizontalListAdapter
import com.yoond.vidaily.adapters.LargeVideoListAdapter
import com.yoond.vidaily.data.VideoMinimal
import com.yoond.vidaily.databinding.FragmentHomeBinding
import com.yoond.vidaily.decorators.ListDecoration
import com.yoond.vidaily.interfaces.OnVideoItemClickListener

/**
 * Shows today's videos, popular videos, subscribing videos
 */
class HomeFragment : Fragment(), OnVideoItemClickListener {

    private lateinit var binding: FragmentHomeBinding
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
        val todayList = dummyData()
        val popularList = todayList.sortedBy { it.views }.reversed()

        val todayAdapter = HomeHorizontalListAdapter(requireContext(), this)
        todayAdapter.submitList(todayList)
        binding.homeRecyclerToday.adapter = todayAdapter

        val popularAdapter = HomeHorizontalListAdapter(requireContext(), this)
        popularAdapter.submitList(popularList)
        binding.homeRecyclerPopular.adapter = popularAdapter

        val followAdapter = LargeVideoListAdapter(requireContext(), this)
        followAdapter.submitList(todayList)
        binding.homeRecyclerFollow.adapter = followAdapter

        val margin = resources.getDimension(R.dimen.video_small_margin).toInt()
        val decoration = ListDecoration(margin, false)

        binding.homeRecyclerToday.addItemDecoration(decoration)
        binding.homeRecyclerPopular.addItemDecoration(decoration)

        val largeMargin = resources.getDimension(R.dimen.video_large_margin).toInt()
        binding.homeRecyclerFollow.addItemDecoration(ListDecoration(largeMargin, true))
    }

    override fun onItemClick(key: String) {
    }

    private fun setBackPressed() {
        if (System.currentTimeMillis() - pressedTimeInMillis > 2000) {
            pressedTimeInMillis = System.currentTimeMillis()
            Toast.makeText(context, resources.getString(R.string.home_toast_back_pressed), Toast.LENGTH_SHORT).show()
        } else {
            activity?.finish()
        }
    }

    private fun dummyData() = listOf<VideoMinimal>(
        VideoMinimal(
            "1",
            "제목 1",
            "사용자 1",
            100,
            "https://storage.googleapis.com/openimages/web/images/oid_thumbnail.png",
            "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"
        ),
        VideoMinimal(
            "2",
            "제목 2",
            "사용자 2",
            10000,
            "https://storage.googleapis.com/openimages/web/images/loc_narr_thumbnail.png",
            "https://storage.googleapis.com/coverr-main/mp4%2Fcoverr-sandboarding-in-desert-1585557594519.mp4"
        ),
        VideoMinimal(
            "3",
            "아주아주아주아주아주아주아주아주아주 긴 제목을 가진 영상 3",
            "사용자 3",
            50000,
            "https://storage.googleapis.com/openimages/web/images/loc_narr_thumbnail.png",
            "https://storage.googleapis.com/coverr-main/mp4%2Fcoverr-sandboarding-in-desert-1585557594519.mp4"
        ),
        VideoMinimal(
            "4",
            "제목 4",
            "사용자 4",
            100,
            "https://storage.googleapis.com/openimages/web/images/oid_thumbnail.png",
            "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"
        ),
        VideoMinimal(
            "5",
            "제목 5",
            "상당히 기이이이이이이인 이름을 가진 사용자 5",
            1500,
            "https://storage.googleapis.com/openimages/web/images/loc_narr_thumbnail.png",
            "https://storage.googleapis.com/coverr-main/mp4%2Fcoverr-sandboarding-in-desert-1585557594519.mp4"
        )
    )
}