package com.bashirli.mymovie.presentation.ui.fragment.auth.getstarted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.enableTransitionAnim
import com.bashirli.mymovie.common.util.gone
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.FragmentGetStartedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedFragment : BaseFragment<FragmentGetStartedBinding>(FragmentGetStartedBinding::inflate) {

    override fun onViewCreateFinished() {
        enableTransitionAnim()
    }

    override fun setup() {

        with(binding){
            val extras= FragmentNavigatorExtras(
                imageView5 to imageView5.transitionName,
                imageView6 to imageView6.transitionName,
                mainCard to mainCard.transitionName,
                buttonCreateAcc to buttonCreateAcc.transitionName
            )
            buttonCreateAcc.setOnClickListener {
                findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToRegisterFragment(),extras)
            }

            buttonGetStartedFirst.setOnClickListener {
                firstLayout.gone()
                secondLayout.visible()
            }

            textLogin.setOnClickListener{

                findNavController().navigate(
                    GetStartedFragmentDirections.actionGetStartedFragmentToLoginFragment(),
                    extras
                )

            }
        }
    }


}