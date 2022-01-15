package com.yoond.vidaily.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignUpResult
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.databinding.FragmentSignupBinding
import java.lang.Exception

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
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(requireContext())

        binding.signupCodeBtn.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val pwd = binding.signupPwd.text.toString()

            if (email == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_email), Toast.LENGTH_SHORT).show()
            } else if (pwd == "") {
                Toast.makeText(requireContext(), resources.getString(R.string.toast_no_pwd), Toast.LENGTH_SHORT).show()
            } // 몇 자 이상인지 등등 조건 추가
            else {
                val attributes = mapOf<String, String>(
                    "email" to email
                )

                AWSMobileClient.getInstance().signUp(email, pwd, attributes, null, object: Callback<SignUpResult> {
                    override fun onResult(result: SignUpResult?) {
                        Log.d("SIGNUP", "회원가입 성공: ${result?.confirmationState}")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("SIGNUP", "회원가입 실패", e)
                    }
                })
//                val options = AuthSignUpOptions.builder()
//                    .userAttribute(AuthUserAttributeKey.email(), email)
//                    .build()
//                Amplify.Auth.signUp(
//                    email,
//                    pwd,
//                    options,
//                    { Log.d("SIGNUP", "회원가입 성공: $it") },
//                    { Log.e("SIGNUP", "회원가입 실패", it) })
        }

        binding.signupSignupBtn.setOnClickListener {
            val code = binding.signupCode.text.toString()

            AWSMobileClient.getInstance().confirmSignUp(email, code, object: Callback<SignUpResult> {
                override fun onResult(result: SignUpResult?) {
                    activity?.runOnUiThread {
                        if (result?.confirmationState == true) {
                            login(email, pwd)
                            Toast.makeText(requireContext(), resources.getString(R.string.toast_signup_success), Toast.LENGTH_SHORT).show()
                            Log.d("SIGNUP", "회원가입 성공: $it")
                            findNavController().navigate(SignupFragmentDirections.actionNavSignupToNavHome())
                        } else {
                            Toast.makeText(requireContext(), resources.getString(R.string.toast_signup_wrong_code), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onError(e: Exception?) {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), resources.getString(R.string.toast_signup_failed), Toast.LENGTH_SHORT).show()
                        Log.e("SIGNUP", "회원가입 실패", e)
                    }
                }
            })
//            Amplify.Auth.confirmSignUp(
//                email,
//                code,
//                {
//                    login(email, pwd)
//                    activity?.runOnUiThread {
//                        Toast.makeText(requireContext(), resources.getString(R.string.toast_signup_success), Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(SignupFragmentDirections.actionNavSignupToNavHome())
//                        Log.d("SIGNUP", "회원가입 성공: $it")
//                    }
//                },
//                { Log.e("SIGNUP", "회원가입 실패", it) })
        }
        }
    }

    private fun login(email: String, pwd: String) {
        AWSMobileClient.getInstance().signIn(email, pwd, null, object: Callback<SignInResult> {
            override fun onResult(result: SignInResult?) {
                Log.d("SIGNUP_LOGIN", "login state: ${result?.signInState}}")
            }

            override fun onError(e: Exception?) {
                Log.e("SIGNUP_LOGIN", "login failed", e)
            }
        })
    }
}