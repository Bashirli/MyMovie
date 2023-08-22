package com.bashirli.mymovie.presentation.ui.fragment.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.databinding.FragmentEditProfileBinding
import com.bashirli.mymovie.presentation.ui.fragment.profile.ProfileMVVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileMVVM>()

    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        with(binding){
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeData(){
        with(viewModel){
            val pb = CustomProgressBar.progressDialog(requireContext())

            liveData.observe(viewLifecycleOwner){
             when(it){
                 is ProfileMVVM.ProfileState.UserData -> {
                     pb.cancel()
                     binding.userData=it.data
                 }
                 is ProfileMVVM.ProfileState.Token->{
                     viewModel.onEvent(ProfileMVVM.ProfileEvent.GetUserData(it.token))
                 }
                 is ProfileMVVM.ProfileState.Error -> {
                     pb.cancel()
                     requireActivity().showToast(it.message,ToastEnum.ERROR)
                 }
                 ProfileMVVM.ProfileState.Loading -> {
                     pb.show()
                 }
                 else -> {Unit}
             }
            }
        }
    }

}