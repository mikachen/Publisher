package com.zoe.publisher.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zoe.publisher.MainActivity
import com.zoe.publisher.R
import com.zoe.publisher.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModel = LoginViewModel(application)


        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_global_FirstFragment)
            }
        }

        loginBtn()
        registerBtn()

        return binding.root
    }


    private fun loginBtn() {
        binding.loginBtn.setOnClickListener {
            val userId = binding.loginUserId.text.toString()
            val userPassword = binding.loginUserPassword.text.toString()

            viewModel.login(userId = userId, userPassword = userPassword)

        }
    }


    private fun registerBtn() {
        binding.registerBtn.setOnClickListener {
            val inputEmail = binding.registerUserEmail.text.toString()
            val inputNickname = binding.registerUserNickname.text.toString()
            val inputUserId = binding.registerUserId.text.toString()
            val inputPassword = binding.registerUserPassword.text.toString()

            viewModel.register(
                inputEmail = inputEmail,
                inputNickname = inputNickname,
                inputUserId = inputUserId,
                inputPassword = inputPassword)
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).btnTitleSetup()
    }
}