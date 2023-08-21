package com.bashirli.mymovie.presentation.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.MoviesFragment
import com.bashirli.mymovie.presentation.ui.fragment.home.tvseries.TvSeriesFragment


class PagerAdapter (fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> MoviesFragment()
            1-> TvSeriesFragment()
            else-> MoviesFragment()
        }
    }
}