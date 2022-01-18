package com.yoond.vidaily.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.databinding.FragmentUploadBinding
import com.yoond.vidaily.utils.getFileFromUri
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Edit the selected video and add a title and descriptions.
 */
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val viewModel: VideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(true)
        (activity as MainActivity).setBottomNavVisible(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO &&
            resultCode == Activity.RESULT_OK &&
            data != null) {
                uploadVideo(data.data!!)
        }
    }
    private fun init() {
        binding.uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "video/*"
            startActivityForResult(intent, REQUEST_VIDEO)
        }
    }

    private fun uploadVideo(uri: Uri) {
        val timeInMillis = System.currentTimeMillis()
        val video = getFileFromUri(uri, requireContext())
        val title = "video title: $timeInMillis"
        val description = "description"

        if (video != null) {
            viewModel.uploadVideo(video, title, description, timeInMillis)
            Log.d("FILE_SELECTED", uri.toString())
        }
    }

    companion object {
        private const val REQUEST_VIDEO = 1001
    }
}