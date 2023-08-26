package com.bashirli.mymovie.presentation.ui.fragment.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val adapter = CategoryDetailsAdapter()
    private val viewModel by viewModels<CategoryDetailsMVVM>()
    private val args by navArgs<CategoryFragmentArgs>()


    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        setRV()
        viewModel.getDetails(args.categoryId)

        with(binding){
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }


    private fun observeData(){
        with(viewModel){
            val pb=CustomProgressBar.progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner){
                when(it){
                    is CategoryDetailsMVVM.CategoryDetailsState.Details -> {
                        pb.cancel()
                        binding.categoryName=it.data.name
                        adapter.updateList(it.data.items ?: emptyList())
                    }
                    is CategoryDetailsMVVM.CategoryDetailsState.Error -> {
                        pb.cancel()
                    }
                    CategoryDetailsMVVM.CategoryDetailsState.Loading -> pb.show()
                }
            }
        }
    }

    private fun setRV(){
        with(binding){
            rvCategory.adapter=adapter

            adapter.onClickMovieItem={
                it.id?.let { id->
                    findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToDetailsFragment(id))
                }

            }

        }
    }

}