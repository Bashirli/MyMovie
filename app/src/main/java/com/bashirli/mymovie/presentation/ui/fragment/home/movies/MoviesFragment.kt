package com.bashirli.mymovie.presentation.ui.fragment.home.movies

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.BASE_IMAGE_URL
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.databinding.FragmentMoviesBinding
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.presentation.ui.fragment.home.HomeFragmentDirections
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter.MovieCategoriesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter.MoviesAdapter
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel by viewModels<MoviesMVVM>()

    private val moviesAdapter= MoviesAdapter()
    private val trendingAdapter= MoviesAdapter()
    private val upcomingAdapter= MoviesAdapter()
    private val forYouAdapter= MoviesAdapter()
    private val categoryAdapter= MovieCategoriesAdapter()

    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {

        binding.apply {
            setRV()
            setAdapterClicks()
        }
    }

    private fun observeData(){
        viewModel.apply {
            val pb=CustomProgressBar.progressDialog(requireContext())
            categoriesModel.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        pb.cancel()
                        it.data?.let {
                            categoryAdapter.updateList(it.genres)
                        }
                    }
                    Status.ERROR->{
                        requireActivity().showToast(it.message?:"Error",ToastEnum.ERROR)
                        pb.cancel()
                    }
                    Status.LOADING->{
                        pb.show()
                    }
                }
            }
            slideImages.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            setupSlideList(it)
                        }
                    }
                    Status.ERROR->{
                        requireActivity().showToast(it.message?:"Error",ToastEnum.ERROR)

                    }
                    Status.LOADING->{

                    }
                }
            }


            pagingMovie.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    moviesAdapter.submitData(it)
                }
            }
            pagingTrendingMovies.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    trendingAdapter.submitData(it)

                }
            }
            pagingUpcomingMovies.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    upcomingAdapter.submitData(it)
                }
            }

            pagingForYouMovies.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    forYouAdapter.submitData(it)
                }
            }
        }
    }

    private fun setRV(){
        binding.apply {
            rvMovies.adapter=moviesAdapter
            rvTrending.adapter=trendingAdapter
            rvNewSeries.adapter=upcomingAdapter
            rvForYou.adapter=forYouAdapter
            rvCategories.adapter=categoryAdapter
        }
    }


    private fun setupSlideList(list:List<ResultModel>){
        val slideList=ArrayList<SlideModel>()

        list.forEach {
            slideList.add(SlideModel(BASE_IMAGE_URL+it.backdropPath))
        }

        binding.imageSlider.setImageList(
            imageList = slideList,
            scaleType = ScaleTypes.FIT
        )
        binding.imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN)
        binding.imageSlider.startSliding(changeablePeriod = 3000L)
    }


    private fun setAdapterClicks(){
        moviesAdapter.onClickMovieItem={
            navigateToDetails(it)
        }

        forYouAdapter.onClickMovieItem={
            navigateToDetails(it)
        }

        upcomingAdapter.onClickMovieItem={
            navigateToDetails(it)
        }

        trendingAdapter.onClickMovieItem={
            navigateToDetails(it)
        }


        moviesAdapter.onLongClickMovieItem={
            callBottomSheet(it)
        }

        trendingAdapter.onLongClickMovieItem={
            callBottomSheet(it)
        }

        forYouAdapter.onLongClickMovieItem={
            callBottomSheet(it)
        }

        upcomingAdapter.onLongClickMovieItem={
            callBottomSheet(it)
        }

        categoryAdapter.onClickCategoryItem={
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(it.id))
        }
    }

    private fun callBottomSheet(it:ResultModel){
        val bottomFragment= MoviesBottomFragment.instance(it)
        bottomFragment.show(requireActivity().supportFragmentManager,"bottomFragment")
    }

    private fun navigateToDetails(it:ResultModel){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id))
    }

}