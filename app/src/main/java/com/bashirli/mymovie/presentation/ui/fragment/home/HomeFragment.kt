package com.bashirli.mymovie.presentation.ui.fragment.home

import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {



    override fun onViewCreateFinished() {

    }

    override fun setup() {
        tabSetup()

    }

    private fun tabSetup(){
        val pagerAdapter= PagerAdapter(this)
        binding.apply {
            viewPager.adapter=pagerAdapter

            viewPager.isUserInputEnabled=false

            TabLayoutMediator(tabLayout,viewPager){tab,position->
                when(position){
                    0->tab.text=resources.getString(R.string.movies)
                    1->tab.text=resources.getString(R.string.tvSeries)
                    else->tab.text=resources.getString(R.string.movies)
                }
            }.attach()



        }

    }


}