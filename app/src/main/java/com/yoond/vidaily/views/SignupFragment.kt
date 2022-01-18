package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentSignupBinding

/**
 * Sign up if the user wants to.
 */
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

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
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_email), Toast.LENGTH_SHORT).show()
            } else if (pwd == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_pwd), Toast.LENGTH_SHORT).show()
            } // 몇 자 이상인지 등등 조건 추가
            else {
                val options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email)
                    .build()
                Amplify.Auth.signUp(
                    email,
                    pwd,
                    options,
                    { Log.d("AMPLIFY_SIGNUP", "회원가입 성공: $it") },
                    { Log.e("AMPLIFY_SIGNUP", "회원가입 실패", it) })
        }

        binding.signupSignupBtn.setOnClickListener {
            val code = binding.signupCode.text.toString()

            Amplify.Auth.confirmSignUp(
                email,
                code,
                { result ->
                    if (result.isSignUpComplete) {
                        login(email, pwd)
                        Log.i("AMPLIFY_SIGNUP", "회원가입 성공: $it")
                    } else {
                        Toast.makeText(requireContext(), resources.getString(R.string.toast_signup_wrong_code), Toast.LENGTH_SHORT).show()
                    }
                },
                { Log.e("AMPLIFY_SIGNUP", "회원가입 실패:", it)}
            )
        }
        }
    }

    private fun login(email: String, pwd: String) {
        Amplify.Auth.signIn(
            email,
            pwd,
            { result ->
                if (result.isSignInComplete) {
                    activity?.runOnUiThread {
                        navigateToProfile()
                    }
                    Log.i("LOGIN", "로그인 성공")
                }
            },
            { Log.i("LOGIN", "로그인 실패: $it") }
        )
    }

    private fun navigateToProfile() {
        findNavController().navigate(SignupFragmentDirections.actionNavSignupToNavProfile())
    }
}