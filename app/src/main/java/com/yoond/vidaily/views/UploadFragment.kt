package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.databinding.FragmentUploadBinding

/**
 * Edit the selected video and add a title and descriptions.
 */
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(true)
        (activity as MainActivity).setBottomNavVisible(false)
    }

}