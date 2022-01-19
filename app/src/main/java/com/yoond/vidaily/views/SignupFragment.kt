package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentSignupBinding
import com.yoond.vidaily.viewmodels.AuthViewModel

/**
 * Sign up if the user wants to.
 */
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(false)
        (activity as MainActivity).setBottomNavVisible(false)
    }

    private fun init() {

        binding.signupCodeBtn.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val pwd = binding.signupPwd.text.toString()

            if (email == "") {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.toast_no_email),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (pwd == "") {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.toast_no_pwd),
                    Toast.LENGTH_SHORT
                ).show()
            } // 몇 자 이상인지 등등 조건 추가
            else {
                authViewModel.signUp(email, pwd)
            }
        }

        binding.signupSignupBtn.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val pwd = binding.signupPwd.text.toString()
            val code = binding.signupCode.text.toString()

            authViewModel.confirmSignUp(email, code).observe(viewLifecycleOwner) { isConfirmed ->
                if (isConfirmed) {
                    authViewModel.login(email, pwd).observe(viewLifecycleOwner) { isLoggedIn ->
                        if (isLoggedIn) {
                            navigateToProfile()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToProfile() {
        findNavController().navigate(SignupFragmentDirections.actionNavSignupToNavProfile())
    }
}