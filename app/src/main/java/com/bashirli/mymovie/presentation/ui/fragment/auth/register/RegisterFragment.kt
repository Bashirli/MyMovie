package com.bashirli.mymovie.presentation.ui.fragment.auth.register

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.DATE_FORMAT
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.enableTransitionAnim
import com.bashirli.mymovie.common.util.gone
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.common.util.validateEmail
import com.bashirli.mymovie.common.util.validateEmptyField
import com.bashirli.mymovie.common.util.validatePassword
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.data.dto.user.UserDTO
import com.bashirli.mymovie.databinding.FragmentRegisterBinding
import com.bashirli.mymovie.presentation.ui.fragment.auth.AuthMVVM
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.rpc.context.AttributeContext.Auth
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<AuthMVVM>()
    private var selectedProfilePhoto : Uri? = null
    private lateinit var photoPicker : ActivityResultLauncher<PickVisualMediaRequest>

    private var token : String=""

    override fun onViewCreateFinished() {
        setupPhotoPicker()
        enableTransitionAnim()
        observeData()

    }

    override fun setup() {
        with(binding) {

            buttonGoBack.setOnClickListener {
                val extras= FragmentNavigatorExtras(
                    imageView5 to imageView5.transitionName,
                    imageView6 to imageView6.transitionName,
                    mainCard to mainCard.transitionName,
                )
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToGetStartedFragment(),extras)
            }
            buttonRegister.setOnClickListener {
                //first
                register()
            }

            buttonContinue.setOnClickListener {
                //second
               otpVerification()
            }

            buttonChangeEmail.setOnClickListener {
                changeLayout(1)
            }

            buttonContinueLast.setOnClickListener {
                //finish
                fillUserInfo()
            }

            editDate.setOnClickListener {
                setDatePicker()
            }

            buttonImageAdd.setOnClickListener {
                photoPickGallery()
            }
        }
    }

    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner){
                when(it){
                    is AuthMVVM.AuthUiState.RegisterUser->{
                        pb.cancel()
                        requireActivity().showToast(resources.getString(R.string.success),ToastEnum.SUCCESS)
                        viewModel.getToken()
                        changeLayout(3)
                    }
                    is AuthMVVM.AuthUiState.CreateUser->{
                        pb.cancel()
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                        requireActivity().showToast(resources.getString(R.string.sucCreated),ToastEnum.SUCCESS)
                    }
                    is AuthMVVM.AuthUiState.UserToken->{
                        pb.cancel()
                        viewModel.saveToken(it.token)
                        token=it.token
                    }

                    is AuthMVVM.AuthUiState.Loading->{
                        pb.show()
                    }
                    is AuthMVVM.AuthUiState.Error->{
                        pb.cancel()
                        requireActivity().showToast(it.message,ToastEnum.ERROR)
                    }
                    else->{}
                }
            }
        }
    }

    private fun register(){
        with(binding){
           if(!validationRegister()){
               return
           }
            changeLayout(2)
        }
    }

    private fun otpVerification(){
       with(binding){
           val otp = pinView.text.toString()
           if(otp.isEmpty()){
               requireActivity().showToast(resources.getString(R.string.fillOtp),ToastEnum.ERROR)
           }else{
               val email = editEmail.text.toString()
               val password = editPass.text.toString()
               viewModel.registerUser(email, password)
           }
       }
    }

    private fun fillUserInfo(){
        with(binding){
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val dateOfBirth = editDate.text.toString()

            if(!validationUser(firstName.trim(),lastName.trim(),dateOfBirth.trim())){
                Log.e("state",true.toString())
                return
            }

            val userDTO = UserDTO(editEmail.text.toString(),firstName,lastName,dateOfBirth,token,selectedProfilePhoto)
            viewModel.createUser(userDTO)
        }
    }

    private fun validationUser(firstName:String , lastName:String, dateOfBirth:String):Boolean{
        with(binding){
            var state = true
            inputFirstName.error=null
            inputLastName.error=null
            inputDate.error=null

            if(!validateEmptyField(firstName)){
                inputFirstName.error = resources.getString(R.string.emptyField)
                state = false
            }
            if(!validateEmptyField(lastName)){
                inputLastName.error = resources.getString(R.string.emptyField)
                state = false
            }
            if(!validateEmptyField(dateOfBirth)){
                inputDate.error = resources.getString(R.string.emptyField)
                state = false
            }

            return state
        }

    }

    private fun validationRegister():Boolean{
        with(binding){
            var state = true
            inputEmail.error = null
            inputPassword.error = null

            val email = editEmail.text.toString()
            val password = editPass.text.toString()

            if(!validateEmail(email)){
                inputEmail.error=resources.getString(R.string.errorEmail)
                state = false
            }
            if(!validatePassword(password)){
                inputPassword.error = resources.getString(R.string.passShort)
                state = false
            }
            if(!validateEmptyField(email)){
                inputEmail.error=resources.getString(R.string.emptyField)
                state = false
            }
            if(!validateEmptyField(password)){
                inputPassword.error=resources.getString(R.string.emptyField)
                state = false
            }

            return state
        }
    }

    private fun photoPickGallery(){
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setupPhotoPicker(){
         photoPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            selectedProfilePhoto = uri
            if (uri != null) {
                binding.imageUser.setImageURI(uri)
            } else {
                requireActivity().showToast(
                    resources.getString(R.string.nothingSelected),
                    ToastEnum.INFO
                )
            }
        }
    }

    private fun makeNumberButtonsBlack() {
        with(binding) {
            cardFirst.background.setTint(resources.getColor(R.color.black))
            cardSecond.background.setTint(resources.getColor(R.color.black))
            cardThirst.background.setTint(resources.getColor(R.color.black))
        }
    }

    private fun setDatePicker() {
        with(binding) {
            val datePicker =
                MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setTitleText(R.string.selectDate)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(requireActivity().supportFragmentManager,"datePicker")

            datePicker.addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat(DATE_FORMAT)
                val date= Date(it)
                val format = sdf.format(date)

                editDate.setText(format)

            }

        }
    }

    private fun changeLayout(showLayoutPosition:Int){
        with(binding){
            makeNumberButtonsBlack()
            when(showLayoutPosition){
                1->{
                    cardFirst.background.setTint(resources.getColor(R.color.blue))
                    layoutSecond.gone()
                    layoutThird.gone()
                    layoutFirst.visible()
                }
                2->{
                    cardSecond.background.setTint(resources.getColor(R.color.blue))
                    layoutSecond.visible()
                    layoutFirst.gone()
                    layoutThird.gone()
                }
                3->{
                    cardThirst.background.setTint(resources.getColor(R.color.blue))
                    layoutSecond.gone()
                    layoutFirst.gone()
                    layoutThird.visible()
                }
                else->{Unit}
            }
        }

    }
}