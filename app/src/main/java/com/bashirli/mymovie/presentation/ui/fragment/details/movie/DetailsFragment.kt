package com.bashirli.mymovie.presentation.ui.fragment.details.movie

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.databinding.FragmentDetailsBinding
import com.bashirli.mymovie.domain.model.details.movie.DetailsModel
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CastAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CompaniesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.CrewAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.GenreAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.ImageAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.ReviewAdapter
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.MoviesBottomFragment
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter.MoviesAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel by viewModels<DetailsMVVM>()
    private val args by navArgs<DetailsFragmentArgs>()
    private val genreAdapter= GenreAdapter()
    private val companyAdapter= CompaniesAdapter()
    private val crewAdapter= CrewAdapter()
    private val imageAdapter= ImageAdapter()
    private val castAdapter= CastAdapter()
    private val recommendationAdapter= MoviesAdapter()
    private val reviewAdapter= ReviewAdapter()

    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        val id=args.movieId
        viewModel.apply {
            getData(id)
            getCredits(id)
            getReviews(id)
            getImages(id)
            getRecommendations(id)
        }
        setRV()
        binding.apply {
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setRV(){
        binding.apply {
            rvGenres.adapter=genreAdapter
            rvCompanies.adapter=companyAdapter
            rvCrew.adapter=crewAdapter
            rvCast.adapter=castAdapter
            rvPhotos.adapter=imageAdapter
            rvRecommendations.adapter=recommendationAdapter
            rvUserReviews.adapter=reviewAdapter
        }


        recommendationAdapter.onLongClickMovieItem={
            val bf= MoviesBottomFragment.instance(it)
            bf.show(requireActivity().supportFragmentManager,"detailsBottomFragment")
        }

        recommendationAdapter.onClickMovieItem={
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentSelf(it.id))
        }

        castAdapter.onClickCardListener = {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToCelebSearchFragment(
                    it.id
                )
            )
        }


    }

    private fun observeData(){
        viewModel.apply {
            val pb=CustomProgressBar.progressDialog(requireContext())

            liveData.observe(viewLifecycleOwner){
                when(it){
                    is DetailsMVVM.State.MovieDetails->{
                        setData(it.data)
                        pb.cancel()
                    }
                    is DetailsMVVM.State.Reviews->{
                        setReviews(it.data)
                    }
                    is DetailsMVVM.State.Images->{
                        setImages(it.data)
                    }
                    is DetailsMVVM.State.Credits->{
                        setCredits(it.data)
                    }
                    is DetailsMVVM.State.Loading->{
                        pb.show()
                    }
                    is DetailsMVVM.State.Error->{
                        pb.cancel()
                        val alert= MaterialAlertDialogBuilder(requireContext())
                        alert.setCancelable(false).setTitle(R.string.error)
                            .setMessage(it.message)
                            .setPositiveButton(R.string.ok){dialog,which->
                                findNavController().popBackStack()
                            }.create()
                        alert.show()

                    }
                }
            }

            recommendationDetails.observe(viewLifecycleOwner){
                viewLifecycleOwner.lifecycleScope.launch {
                    recommendationAdapter.submitData(it)
                }
            }
        }
    }

    private fun setData(it: DetailsModel){
        with(binding){
            detailsModel=it
            genreAdapter.updateList(it.genres)
            companyAdapter.updateList(it.productionCompanies)
        }
    }

    private fun setCredits(it:CastBaseModel){
        binding.apply {
            crewAdapter.updateList(it.crew)
            castAdapter.updateList(it.cast)
        }
    }

    private fun setReviews(it:ReviewModel){
        reviewAdapter.updateList(it.results)

        reviewAdapter.onClickMoreButton={
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToReviewsFragment(
                    it
                )
            )
        }
    }

    private fun setImages(it:ImagesModel){
        imageAdapter.updateList(it.backdrops)
        imageAdapter.onClickMoreButton={
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToImagesFragment(
                    it
                )
            )
        }
    }



}