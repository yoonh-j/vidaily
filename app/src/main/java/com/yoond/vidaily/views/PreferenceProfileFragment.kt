package com.yoond.vidaily.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.amplifyframework.datastore.generated.model.User
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentPreferenceProfileBinding
import com.yoond.vidaily.utils.SHARED_PREFS_CURRENT_USER
import com.yoond.vidaily.utils.SHARED_PREFS_PROFILE_URL
import com.yoond.vidaily.utils.getFileFromUri
import com.yoond.vidaily.viewmodels.UserViewModel
import java.io.File

/**
 * 기존의 프로필 편집.
 */
class PreferenceProfileFragment : Fragment() {
    private lateinit var binding: FragmentPreferenceProfileBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var gson: Gson
    private lateinit var user: User
    private var selectedUri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreferenceProfileBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)!!
        gson = Gson()
        user = getCurUserFromSharedPrefs()

        binding.user = user
        setProfileImage()
        setBtnClick()
    }

    private fun setProfileImage() {
        val url = sharedPrefs.getString(SHARED_PREFS_PROFILE_URL, "")
        if (url != null) {
            Glide.with(this)
                .load(url)
                .placeholder(R.color.black)
                .into(binding.prefProfile)
        }
        binding.prefProfile.setOnClickListener {
            selectImage()
        }
    }

    private fun setBtnClick() {
        binding.prefBtn.setOnClickListener {
            val username = binding.prefUsername.text.toString()

            if (username == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_username), Toast.LENGTH_LONG).show()
            } else if (selectedUri == Uri.EMPTY) {
                // 프로필 사진을 변경하지 않음
                updateCurUser(username, null)
                navigateUp()
            } else {
                // 프로필 사진을 변경함
                val profileImage = getFileFromUri(selectedUri, requireContext())
                if (profileImage != null) {
                    updateCurUser(username, profileImage)
                    navigateUp()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE &&
            resultCode == Activity.RESULT_OK &&
            data != null) {
            selectedUri = data.data!!
            setImageVisible()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun setImageVisible() {
        Glide.with(this)
            .load(selectedUri)
            .into(binding.prefProfile)
    }

    private fun getCurUserFromSharedPrefs(): User {
        val json = sharedPrefs.getString(SHARED_PREFS_CURRENT_USER, "")

        return gson.fromJson(json, User::class.java)
    }

    private fun updateCurUser(username: String, profileImage: File?) {
        userViewModel.updateUser(user, username, profileImage).observe(viewLifecycleOwner) {
            val userPref = gson.toJson(it)

            sharedPrefs.edit()
                .putString(SHARED_PREFS_CURRENT_USER, userPref)
                .apply()
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    companion object {
        private const val REQUEST_IMAGE = 1003
    }
}