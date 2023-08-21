package com.bashirli.mymovie.presentation.ui.fragment.celebDetails

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.common.util.gone
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.FragmentCelebSearchBinding
import com.bashirli.mymovie.presentation.ui.fragment.celebDetails.adapter.CelebMoviesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.celebDetails.adapter.CelebTvSeriesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.search.adapter.CelebsPagingAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CelebSearchFragment : BaseFragment<FragmentCelebSearchBinding>(FragmentCelebSearchBinding::inflate) {

    private val viewModel by viewModels<CelebsSearchMVVM>()
    private val args by navArgs<CelebSearchFragmentArgs>()
    private val celebAdapter=CelebsPagingAdapter()
    private val movieAdapter =  CelebMoviesAdapter()
    private val tvSeriesAdapter =  CelebTvSeriesAdapter()


    private val personId by lazy {
        args.celebId
    }


    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        viewModel.getCelebDetails(personId)
        viewModel.getCelebMovies(personId)
        viewModel.getCelebTvSeries(personId)
        setRV()
        with(binding){

            cardCeleb.setOnClickListener {
                if(layoutBio.visibility==View.VISIBLE){
                    layoutBio.gone()
                    textView13.gone()
                }else{
                    layoutBio.visible()
                    textView13.visible()
                }
            }

            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }


        }
    }

    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())
            celebData.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        pb.cancel()
                        it.data?.let {
                            binding.celebData=it
                        }
                    }
                    Status.ERROR->{
                        pb.cancel()
                        val alert= MaterialAlertDialogBuilder(requireContext())
                        alert.setCancelable(false).setTitle(R.string.error)
                            .setMessage(it.message?:"Error")
                            .setPositiveButton(R.string.ok){dialog,which->
                                findNavController().popBackStack()
                            }.create()
                        alert.show()
                    }
                    Status.LOADING->{
                        pb.show()
                    }
                }
            }

            celebRecommendationData.observe(viewLifecycleOwner){
                lifecycleScope.launch {
                    celebAdapter.submitData(it)
                }
            }

            celebMoviesData.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        pb.cancel()
                        it.data?.let {
                            movieAdapter.updateList(it.celebCast)
                        }
                    }
                    Status.ERROR->{
                        pb.cancel()
                        val alert= MaterialAlertDialogBuilder(requireContext())
                        alert.setCancelable(false).setTitle(R.string.error)
                            .setMessage(it.message?:"Error")
                            .setPositiveButton(R.string.ok){dialog,which->
                                findNavController().popBackStack()
                            }.create()
                        alert.show()
                    }
                    Status.LOADING->{
                        pb.show()
                    }
                }
            }
            celebTvSeriesData.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        pb.cancel()
                        it.data?.let {
                            tvSeriesAdapter.updateList(it.celebTvCast)
                        }
                    }
                    Status.ERROR->{
                        pb.cancel()
                        Log.e("errorMessage",it.message?:"")
                        val alert= MaterialAlertDialogBuilder(requireContext())
                        alert.setCancelable(false).setTitle(R.string.error)
                            .setMessage(it.message?:"Error")
                            .setPositiveButton(R.string.ok){dialog,which->
                                findNavController().popBackStack()
                            }.create()
                        alert.show()
                    }
                    Status.LOADING->{
                        pb.show()
                    }
                }
            }
        }
    }

    private fun setRV(){
        with(binding){
            rvCelebs.adapter = celebAdapter
            rvMovies.adapter = movieAdapter
            rvTvSeries.adapter = tvSeriesAdapter

            tvSeriesAdapter.onClickTvSeriesItem={
                it.id?.let {
                    findNavController().navigate(
                        CelebSearchFragmentDirections.actionCelebSearchFragmentToTvSeriesDetailsFragment(it)
                    )
                }
            }

            celebAdapter.onClickCelebsItem={
                it.id?.let {
                    findNavController().navigate(CelebSearchFragmentDirections.actionCelebSearchFragmentSelf(it))
                }
            }

            movieAdapter.onClickMovieItem={
                it.id?.let {
                    findNavController().navigate(CelebSearchFragmentDirections.actionCelebSearchFragmentToDetailsFragment(it))
                }
            }
        }
    }
}