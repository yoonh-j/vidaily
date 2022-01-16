package com.yoond.vidaily.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amazonaws.util.IOUtils
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.databinding.FragmentUploadBinding
import com.yoond.vidaily.viewmodels.StorageViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Edit the selected video and add a title and descriptions.
 */
class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private val viewModel: StorageViewModel by viewModels()

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
                uploadVideoTemp(data.data!!)
        }
    }
    private fun init() {
        binding.uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "video/*"
            startActivityForResult(intent, REQUEST_VIDEO)
        }
    }

    private fun uploadVideoTemp(uri: Uri) {
        val key = System.currentTimeMillis().toString()
        val video = getFileFromUri(uri)

        if (video != null) {
            viewModel.uploadVideo(key, video)
            Log.d("FILE_SELECTED", uri.toString())
        }
    }

    private fun getPathFromUri(uri: Uri): String {
        var path = ""
        val cursor = activity?.contentResolver?.query(uri, null, null, null, null)

        if (cursor != null) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            path = cursor.getString(nameIndex)
            cursor.close()
        }
        return path
    }

    /**
     * Copies the selected file into the internal storage
     */
    private fun getFileFromUri(uri: Uri): File? {
        var file: File? = null
        val fileDescriptor = context?.contentResolver?.openFileDescriptor(uri, "r", null)
        fileDescriptor?.let {
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            file = File(context?.cacheDir, getPathFromUri(uri))
            val outputStream = FileOutputStream(file)
            IOUtils.copy(inputStream, outputStream)
        }
        return file
    }

    companion object {
        private const val REQUEST_VIDEO = 1001
    }
}