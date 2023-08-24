package com.bashirli.mymovie.presentation.ui.fragment.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.CustomProgressBar
import com.bashirli.mymovie.common.util.ToastEnum
import com.bashirli.mymovie.common.util.showToast
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.databinding.FragmentFavoritesBinding
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import com.google.android.material.snackbar.Snackbar
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

            adapter.onClickFavoritesItem={
                it.id?.let {
                    findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(it))
                }
            }

            adapter.onClickHeartButton={position->
                deleteFavItem(position)
            }
        }
    }

    private fun deleteFavItem(position:Int){
            val adapterList = ArrayList(adapter.getAdapterList())
            val item = adapterList.get(position)

            item.id?.let {

                Log.e("test",adapterList.toString())
                adapterList.removeAt(position)
                viewModel.deleteItem(it)

                Log.e("test3",position.toString())
                Log.e("test3",adapterList.toString())
                adapter.updateList(adapterList)
                Snackbar.make(requireView(),R.string.itemDeleted,Snackbar.LENGTH_LONG).setAction(R.string.undo){
                    adapterList.add(position,item)
                    adapter.updateList(adapterList)
                    viewModel.insertItem(FavoritesDTO(
                        item.id,item.title,item.voteAverage,item.releaseYear,item.image
                    ))


                    Log.e("test2",position.toString())
                    Log.e("test2",adapterList.toString())
                }.show()

            }
        }
}