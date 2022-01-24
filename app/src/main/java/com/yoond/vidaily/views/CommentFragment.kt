package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.databinding.FragmentCommentBinding

/**
 * Shows the user's comments list
 */
class CommentFragment : Fragment() {

    private lateinit var binding: FragmentCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }
}