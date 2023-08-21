package com.bashirli.mymovie.presentation.ui.fragment.home.tvseries

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.BASE_IMAGE_URL
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.databinding.FragmentTvSeriesBinding
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesModel
import com.bashirli.mymovie.presentation.ui.fragment.home.HomeFragmentDirections
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter.MovieCategoriesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.home.tvseries.adapter.TvSeriesPagingAdapter
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvSeriesFragment : BaseFragment<FragmentTvSeriesBinding>(FragmentTvSeriesBinding::inflate) {

    private val viewModel by viewModels<TvSeriesMVVM>()
    private val categoryAdapter= MovieCategoriesAdapter()
    private val airingAdapter = TvSeriesPagingAdapter()
    private val onTheAirAdapter = TvSeriesPagingAdapter()
    private val trendingAdapter = TvSeriesPagingAdapter()
    private val discoverAdapter = TvSeriesPagingAdapter()
    private val topRatedAdapter = TvSeriesPagingAdapter()
    private val popularAdapter = TvSeriesPagingAdapter()


    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        setRV()
    }

    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())

            liveData.observe(viewLifecycleOwner){
                when(it){
                    is TvSeriesMVVM.State.Images->{
                        with(it.data){
                            setImage(this)
                        }
                    }
                    is TvSeriesMVVM.State.Categories->{
                        categoryAdapter.updateList(it.data.genres)
                        pb.cancel()
                    }

                    is TvSeriesMVVM.State.Loading ->{
                        pb.show()
                    }
                    is TvSeriesMVVM.State.Error->{
                        pb.cancel()
                    }
                }
            }

            airingTodayData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    airingAdapter.submitData(it)
                }
            }

            onTheAirData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    onTheAirAdapter.submitData(it)
                }
            }
            popularData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    popularAdapter.submitData(it)
                }
            }
            trendingData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    trendingAdapter.submitData(it)
                }
            }
            discoverData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    discoverAdapter.submitData(it)
                }
            }
            topRatedData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    topRatedAdapter.submitData(it)
                }
            }


        }
    }

    private fun setRV(){
        with(binding){
            rvAiringToday.adapter=airingAdapter
            rvDiscover.adapter=discoverAdapter
            rvPopular.adapter=popularAdapter
            rvOnTheAir.adapter=onTheAirAdapter
            rvTrending.adapter=trendingAdapter
            rvCategory.adapter=categoryAdapter
            rvTopRated.adapter=topRatedAdapter

            trendingAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
            airingAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
            popularAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
            topRatedAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
            discoverAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
            onTheAirAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTvSeriesDetailsFragment(it))
                }
            }
        }
    }

    private fun setImage(data : TvSeriesModel){
        val imageList=ArrayList<SlideModel>()
        data.tvSeriesResults.forEach {
            imageList.add(SlideModel(BASE_IMAGE_URL +it.backdropPath))

        }

        binding.imageSlider.setImageList(
            imageList = imageList,
            scaleType = ScaleTypes.FIT
        )
        binding.imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN)
        binding.imageSlider.startSliding(changeablePeriod = 3000L)

    }



}