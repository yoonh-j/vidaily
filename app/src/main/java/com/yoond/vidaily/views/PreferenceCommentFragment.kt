package com.yoond.vidaily.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.adapters.MyCommentListAdapter
import com.yoond.vidaily.databinding.FragmentPreferenceCommentBinding
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Shows the user's comments list
 */
class PreferenceCommentFragment : Fragment() {

    private lateinit var binding: FragmentPreferenceCommentBinding
    private val videoViewModel: VideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreferenceCommentBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val commentAdapter = MyCommentListAdapter.instance
        binding.commentRecycler.adapter = commentAdapter
        subscribeUi(commentAdapter)
    }

    private fun subscribeUi(commentAdapter: MyCommentListAdapter) {
        val uId = Amplify.Auth.currentUser.userId

        videoViewModel.getCommentsByUser(uId).observe(viewLifecycleOwner) {
            commentAdapter.submitList(it)
        }
    }
}