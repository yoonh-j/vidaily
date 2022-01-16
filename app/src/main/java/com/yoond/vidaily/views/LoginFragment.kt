package com.yoond.vidaily.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentLoginBinding

/**
 * Login if the user hasn't signed in yet
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setBackPressed()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarVisible(false)
        (activity as MainActivity).setBottomNavVisible(false)
    }

    private fun init() {
        binding.loginSignup.setOnClickListener {
            findNavController().navigate(R.id.nav_signup)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val pwd = binding.loginPwd.text.toString()

            if (email == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_email), Toast.LENGTH_SHORT).show()
            } else if (pwd == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_pwd), Toast.LENGTH_SHORT).show()
            } // 몇 자 이상인지 등등 조건 추가
            else {
                Amplify.Auth.signIn(email, pwd,
                    { result ->
                        if (result.isSignInComplete) {
                            Log.i("AMPLIFY_LOGIN", "로그인 성공")
                            activity?.runOnUiThread {
                                findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavHome())
                            }
                        }
                    },
                    { Log.e("AMPLIFY_LOGIN", "로그인 실패:", it) }
                )
            }
        }
    }

    private fun setBackPressed() {
        activity?.finish()
    }
}