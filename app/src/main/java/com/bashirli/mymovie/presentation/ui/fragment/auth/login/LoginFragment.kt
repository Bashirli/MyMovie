package com.bashirli.mymovie.presentation.ui.fragment.auth.login

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.enableTransitionAnim
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.common.util.validateEmail
import com.bashirli.mymovie.common.util.validateEmptyField
import com.bashirli.mymovie.common.util.validatePassword
import com.bashirli.mymovie.databinding.FragmentLoginBinding
import com.bashirli.mymovie.presentation.ui.fragment.auth.AuthMVVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<AuthMVVM>()



    override fun onViewCreateFinished() {
        enableTransitionAnim()
        observeData()
    }

    override fun setup() {
        with(binding){
            buttonGoBack.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToGetStartedFragment())
            }

            buttonLogin.setOnClickListener {
                login()
            }
        }
    }

    private fun login(){

        val email = binding.editEmail.text.toString()
        val password = binding.editPass.text.toString()

        if(!validate(email.trim(),password.trim())){
            return
        }


        viewModel.loginUser(email, password)

    }

    private fun validate(email:String,password:String) : Boolean{
        with(binding){
            var state = true
            inputEmail.error=null
            inputPassword.error=null
            if(!validateEmptyField(email)){
                state=false
                inputEmail.error=resources.getString(R.string.emptyField)
            }
            if(!validateEmptyField(password)){
                state=false
                inputPassword.error=resources.getString(R.string.emptyField)
            }
            if(!validateEmail(email)){
                state=false
                inputEmail.error=resources.getString(R.string.errorEmail)
            }
            if(!validatePassword(password)){
                state=false
                inputPassword.error=resources.getString(R.string.passShort)
            }


            return state
        }

    }


    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner){
                when(it){
                    is AuthMVVM.AuthUiState.Login->{
                        viewModel.getToken()
                        pb.cancel()
                    }
                    is AuthMVVM.AuthUiState.UserToken->{
                        viewModel.saveToken(it.token)
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        pb.cancel()
                    }
                    is AuthMVVM.AuthUiState.Loading->{
                        pb.show()
                    }

                    is AuthMVVM.AuthUiState.Error->{
                        pb.cancel()
                        requireActivity().showToast(it.message,ToastEnum.ERROR)
                    }
                    else ->{Unit}
                }
            }
        }
    }

}

