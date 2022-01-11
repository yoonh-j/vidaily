package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentVideoBinding

/**
 * Shows the video, uploaded user and comments
 */
class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }
}