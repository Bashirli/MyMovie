package com.bashirli.mymovie.presentation.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashMVVM>()
    private var job: Job?=null
    private var status:Boolean = false

    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        job = lifecycleScope.launch {
            delay(2500)
            if(status){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToGetStartedFragment())
            }
        }
    }

    private fun observeData(){
        viewModel.isAuth.observe(viewLifecycleOwner){
            status = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}