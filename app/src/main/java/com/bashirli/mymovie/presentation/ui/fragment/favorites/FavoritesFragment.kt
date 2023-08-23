package com.bashirli.mymovie.presentation.ui.fragment.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.databinding.FragmentFavoritesBinding
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModels<FavoritesMVVM>()
    private val adapter = FavoritesAdapter()
    override fun onViewCreateFinished() {
        observeData()
    }

    override fun setup() {
        setRV()
    }

    private fun observeData(){
        with(viewModel){
            val pb = CustomProgressBar.progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner){
                when(it){
                    is FavoritesMVVM.FavoritesState.Error -> {
                        pb.cancel()
                        requireActivity().showToast(it.message,ToastEnum.ERROR)
                    }
                    is FavoritesMVVM.FavoritesState.Favorites -> {
                        pb.cancel()
                        adapter.updateList(it.data)
                    }
                    FavoritesMVVM.FavoritesState.Loading -> pb.show()
                }
            }
        }
    }


    private fun setRV(){
        with(binding){
            rvFavorites.adapter=adapter
        }
    }

}