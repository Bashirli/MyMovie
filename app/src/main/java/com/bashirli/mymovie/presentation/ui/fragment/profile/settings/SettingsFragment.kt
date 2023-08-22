package com.bashirli.mymovie.presentation.ui.fragment.profile.settings

import android.os.Bundle
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
import com.bashirli.mymovie.common.util.reset
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.databinding.FragmentSettingsBinding
import com.bashirli.mymovie.presentation.ui.fragment.profile.ProfileMVVM
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

      private val viewModel by viewModels<ProfileMVVM>()

      override fun onViewCreateFinished() {
            observeData()
      }

      override fun setup() {
            with(binding){
                  buttonGoBack.setOnClickListener {
                        findNavController().popBackStack()
                  }
                  buttonLogOut.setOnClickListener {
                        logOut()
                  }
            }
      }

      private fun observeData(){
            with(viewModel){
                  val pb = CustomProgressBar.progressDialog(requireContext())
                  liveData.observe(viewLifecycleOwner){
                        when(it){
                              is ProfileMVVM.ProfileState.Error -> {
                                    pb.cancel()
                                    requireActivity().showToast(it.message,ToastEnum.ERROR)
                              }
                              ProfileMVVM.ProfileState.Loading -> {
                                    pb.show()
                              }
                              ProfileMVVM.ProfileState.LogOut -> {
                                    pb.cancel()
                                    viewModel.onEvent(ProfileMVVM.ProfileEvent.RemoveToken)
                              }
                              ProfileMVVM.ProfileState.TokenRemover -> {
                                    pb.cancel()
                                    requireActivity().reset()
                              }
                              else ->{Unit}
                        }
                  }

            }
      }

      private fun logOut(){

            val alert = MaterialAlertDialogBuilder(requireContext())

            alert.setTitle(R.string.attention).setMessage(R.string.logOutMessage)
                  .setCancelable(false)
                  .setPositiveButton(R.string.yes){dialog,which->
                        viewModel.onEvent(ProfileMVVM.ProfileEvent.LogOut)
                  }.setNegativeButton(R.string.no){dialog,which->
                        dialog.dismiss()
                  }.create().show()

      }

}