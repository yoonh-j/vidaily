package com.yoond.vidaily.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentProfileBinding
import com.yoond.vidaily.utils.getFileFromUri
import com.yoond.vidaily.viewmodels.UserViewModel

/**
 * user can set his/her profile after the sign up
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by viewModels()
    private var imageUri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE &&
            resultCode == Activity.RESULT_OK &&
            data != null) {
                imageUri = data.data!!
                setImageVisible()
        }
    }

    private fun init() {
        binding.profileImage.setOnClickListener {
            selectImage()
        }

        binding.profileBtn.setOnClickListener {
            val username = binding.profileUsername.text.toString()

            if (imageUri == Uri.EMPTY) {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_image), Toast.LENGTH_LONG).show()
            } else if (username == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_username), Toast.LENGTH_LONG).show()
            } else {
                val profileImage = getFileFromUri(imageUri, requireContext())
                if (profileImage != null) {
                    userViewModel.uploadUser(profileImage, username)
                    navigateToHome()
                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun setImageVisible() {
        Glide.with(this)
            .load(imageUri)
            .into(binding.profileImage)
    }

    private fun navigateToHome() {
        findNavController().navigate(ProfileFragmentDirections.actionNavProfileToNavHome())
    }

    companion object {
        private const val REQUEST_IMAGE = 1002
    }
}