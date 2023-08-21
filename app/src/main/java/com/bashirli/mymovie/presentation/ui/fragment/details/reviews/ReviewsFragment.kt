package com.bashirli.mymovie.presentation.ui.fragment.details.reviews

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.databinding.FragmentReviewsBinding
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.FullReviewsAdapter
import com.bashirli.mymovie.presentation.ui.fragment.details.reviews.ReviewsFragmentArgs

class ReviewsFragment() : BaseFragment<FragmentReviewsBinding>(FragmentReviewsBinding::inflate) {

    private val adapter= FullReviewsAdapter()
    private val args by navArgs<ReviewsFragmentArgs>()


    override fun onViewCreateFinished() {
        val list = args.reviewModel.results

        binding.rvUserReviews.adapter = adapter
        adapter.updateList(list)
    }

    override fun setup() {
        binding.apply {
           buttonGoBack.setOnClickListener {
               findNavController().popBackStack()
           }


        }
    }

}