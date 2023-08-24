package com.bashirli.mymovie.presentation.ui.fragment.search

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.gone
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.FragmentHomeBinding
import com.bashirli.mymovie.databinding.FragmentSearchBinding
import com.bashirli.mymovie.databinding.FragmentSettingsBinding
import com.bashirli.mymovie.databinding.LayoutFilterBinding
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.MoviesBottomFragment
import com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter.MoviesAdapter
import com.bashirli.mymovie.presentation.ui.fragment.search.adapter.CelebsPagingAdapter
import com.bashirli.mymovie.presentation.ui.fragment.search.adapter.SearchPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModels<SearchMVVM>()
    private val recommendationAdapter = MoviesAdapter()
    private val celebsAdapter = CelebsPagingAdapter()
    private val searchAdapter = SearchPagingAdapter()


    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        setRV()
        setSearchTextListener()
        with(binding) {

            layoutSearch.gone()
            layoutInitial.visible()

            buttonFilter.setOnClickListener {
                val dialog = Dialog(requireContext())
                val dialogBinding = LayoutFilterBinding.inflate(layoutInflater)
                with(dialog) {
                    setContentView(dialogBinding.root)
                    setCancelable(false)
                    window!!.setBackgroundDrawable(context.getDrawable(R.drawable.blur_background))
                    window!!.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT
                    )
                    create()
                    show()

                    dialogBinding.buttonClose.setOnClickListener {
                        dismiss()
                    }


                }
            }


        }

    }

    private fun observeData(){
        with(viewModel){
            recommendationData.observe(viewLifecycleOwner){
                lifecycleScope.launch {
                    recommendationAdapter.submitData(it)
                }
            }

            celebsData.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    celebsAdapter.submitData(it)
                }
            }

            searchData.observe(viewLifecycleOwner) {pagingData->
                lifecycleScope.launch {
                    searchAdapter.submitData(pagingData)
                    Log.e("search", "adapter size ${searchAdapter.itemCount}")

                    view?.let {
                        searchAdapter.loadStateFlow.collectLatest {
                            with(binding){
                                when(it.refresh){
                                    is LoadState.Loading->{
                                        progressBar.visible()

                                    }
                                    is LoadState.NotLoading->{
                                        progressBar.gone()

                                        if (layoutSearch.visibility == View.GONE && searchAdapter.itemCount>0 ) {
                                            layoutSearch.visible()
                                            layoutInitial.gone()
                                        }
                                    }
                                    is LoadState.Error->{
                                        progressBar.gone()
                                        (it.refresh as LoadState.Error).error.localizedMessage?.let {
                                            requireActivity().showToast(it,ToastEnum.ERROR)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }


        }
    }


    private fun setRV(){
        binding.apply {
            rvRecommendations.adapter = recommendationAdapter
            rvCelebs.adapter = celebsAdapter
            rvSearchMovies.adapter = searchAdapter


            recommendationAdapter.onLongClickMovieItem = {
                val bs = MoviesBottomFragment.instance(it)
                bs.show(requireActivity().supportFragmentManager, "searchBS")
            }

            recommendationAdapter.onClickMovieItem = {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                        it.id
                    )
                )
            }

            celebsAdapter.onClickCelebsItem = {
                it.id?.let {
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToCelebSearchFragment(
                            it
                        )
                    )
                }
            }


            searchAdapter.onClickGoButtonListener={
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it.id))
            }

            searchAdapter.onLongClickCardListener={
                val bs= MoviesBottomFragment.instance(it)
                bs.show(requireActivity().supportFragmentManager,"searchBottom")
            }

        }
    }

    private fun setSearchTextListener(){
        with(binding) {
            var job : Job?=null
            editSearch.addTextChangedListener { searchEditable ->
                val query = searchEditable?.toString()
                job?.cancel()
                if (!query.isNullOrEmpty()) {
                    job=lifecycleScope.launch {
                        progressBar.visible()
                        delay(400)
                        viewModel.getSearch(query)
                    }
                } else {
                    layoutSearch.gone()
                    layoutInitial.visible()
                }
            }
        }
    }


}