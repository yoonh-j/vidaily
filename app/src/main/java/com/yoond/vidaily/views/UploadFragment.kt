package com.yoond.vidaily.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentUploadBinding
import com.yoond.vidaily.utils.getFileFromUri
import com.yoond.vidaily.viewmodels.VideoViewModel

/**
 * Edit the selected video and add a title and descriptions.
 */
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var videoUri: Uri
    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        selectVideo()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.playWhenReady = false
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO &&
            resultCode == Activity.RESULT_OK &&
            data != null) {
                videoUri = data.data!!
                initPlayer(videoUri)
        } else {
            navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_upload, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_upload_done -> {
                uploadVideo(videoUri)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selectVideo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO)
    }

    private fun initPlayer(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)

        exoPlayer = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.uploadVideo.player = exoPlayer
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = false
            }
    }

    private fun uploadVideo(uri: Uri) {
        val title = binding.uploadTitle.text.toString()

        if (title == "") {
            Toast.makeText(requireContext(), resources.getString(R.string.toast_no_title), Toast.LENGTH_LONG).show()
        } else {
            val timeInMillis = System.currentTimeMillis()
            val video = getFileFromUri(uri, requireContext())
            val description = binding.uploadDescription.text.toString()

            if (video != null) {
                viewModel.uploadVideo(video, title, description, timeInMillis)
            }
            navigateUp()
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    companion object {
        private const val REQUEST_VIDEO = 1001
    }
}