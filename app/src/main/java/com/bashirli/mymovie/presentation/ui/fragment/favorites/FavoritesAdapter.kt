package com.bashirli.mymovie.presentation.ui.fragment.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.diffUtil.diffList
import com.bashirli.mymovie.databinding.ItemFavoritesBinding
import com.bashirli.mymovie.domain.model.local.FavoritesModel

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val favList = ArrayList<FavoritesModel>()


    inner class FavoritesViewHolder(private val binding : ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:FavoritesModel){
            binding.favoritesModel=item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layout = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = favList[position]
        with(holder){
            bind(item)
        }


    }

    fun updateList(list: List<FavoritesModel>){
        diffList(favList,list) { a, b -> a.id == b.id }.dispatchUpdatesTo(this)
        favList.clear()
        favList.addAll(list)

    }

    fun getAdapterList():List<FavoritesModel> = favList


}