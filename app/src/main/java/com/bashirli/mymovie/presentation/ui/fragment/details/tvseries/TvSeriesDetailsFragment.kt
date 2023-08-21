package com.bashirli.mymovie.presentation.ui.fragment.details.tvseries

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesResult
import com.bashirli.mymovie.databinding.FragmentTvSeriesDetailsBinding
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.details.tvseries.TvSeriesDetailsModel
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.DetailsFragmentDirections
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CastAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CompaniesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CrewAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.GenreAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.ImageAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.ReviewAdapter
import com.bashirli.mymovie.presentation.ui.fragment.home.tvseries.adapter.TvSeriesPagingAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class  TvSeriesDetailsFragment : BaseFragment<FragmentTvSeriesDetailsBinding>(FragmentTvSeriesDetailsBinding::inflate) {

    private val viewModel by viewModels<TvSeriesDetailsMVVM>()
    private val args by navArgs<TvSeriesDetailsFragmentArgs>()
    private val seriesId : Int by lazy {
        args.seriesId
    }

    private val genreAdapter = GenreAdapter()
    private val castAdapter = CastAdapter()
    private val crewAdapter = CrewAdapter()
    private val imagesAdapter = ImageAdapter()
    private val reviewAdapter = ReviewAdapter()
    private val companiesAdapter = CompaniesAdapter()
    private val recommendationsAdapter = TvSeriesPagingAdapter()




    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        setRV()
        call()
        with(binding){
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun call(){
        with(viewModel){
            getDetails(seriesId)
            getCredits(seriesId)
            getImages(seriesId)
            getReviews(seriesId)
            getRecommendations(seriesId)
        }
    }

    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())

            liveData.observe(viewLifecycleOwner){
                when(it){
                    is TvSeriesDetailsMVVM.State.DetailsData -> {
                        pb.cancel()
                        setData(it.data)
                    }
                    is TvSeriesDetailsMVVM.State.ReviewsData -> {
                        setReviews(it.data)
                    }
                    is TvSeriesDetailsMVVM.State.ImagesData -> {
                        setImages(it.data)
                    }
                    is TvSeriesDetailsMVVM.State.CreditsData -> {
                        setCredits(it.data)
                    }
                    is TvSeriesDetailsMVVM.State.Error -> {
                        pb.cancel()
                        val alert= MaterialAlertDialogBuilder(requireContext())
                        alert.setCancelable(false).setTitle(R.string.error)
                            .setMessage(it.message)
                            .setPositiveButton(R.string.ok){dialog,which->
                                findNavController().popBackStack()
                            }.create()
                        alert.show()
                    }
                    is TvSeriesDetailsMVVM.State.Loading -> {
                        pb.show()
                    }

                }
            }

            recommendationsData.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    recommendationsAdapter.submitData(it)
                }
            }

        }
    }

    private fun setRV(){
        with(binding){
            rvCast.adapter=castAdapter
            rvCrew.adapter=crewAdapter
            rvCompanies.adapter=companiesAdapter
            rvGenres.adapter=genreAdapter
            rvPhotos.adapter=imagesAdapter
            rvUserReviews.adapter=reviewAdapter
            rvRecommendations.adapter=recommendationsAdapter

            recommendationsAdapter.onClickSeriesItem={
                it.id?.let {
                    findNavController().navigate(
                        TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentSelf(it)
                    )
                }

            }

        }
    }

    private fun setData(data:TvSeriesDetailsModel){
        binding.tvSeriesData=data


        with(data){
            genreAdapter.updateList(genres)
            companiesAdapter.updateList(productionCompanies)
        }

    }

    private fun setCredits(it: CastBaseModel){

            crewAdapter.updateList(it.crew)
            castAdapter.updateList(it.cast)
            castAdapter.onClickCardListener={
                findNavController().navigate(
                    TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentToCelebSearchFragment(it.id)
                )
            }

    }

    private fun setReviews(it: ReviewModel){
        reviewAdapter.updateList(it.results)

        reviewAdapter.onClickMoreButton={
            findNavController().navigate(TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentToReviewsFragment(
                it
            ))
        }
    }

    private fun setImages(it: ImagesModel){
        imagesAdapter.updateList(it.backdrops)
        imagesAdapter.onClickMoreButton={
            findNavController()
                .navigate(
                    TvSeriesDetailsFragmentDirections.actionTvSeriesDetailsFragmentToImagesFragment(it)
                )
        }
    }


}