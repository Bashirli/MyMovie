package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemCompanyBinding
import com.bashirli.mymovie.domain.model.details.movie.ProductionCompanyModel

class CompaniesAdapter : RecyclerView.Adapter<CompaniesAdapter.CompaniesViewHolder>() {

    inner class CompaniesViewHolder(private val binding:ItemCompanyBinding)  : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductionCompanyModel){
            binding.companyModel=item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesViewHolder {
        val layout=ItemCompanyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CompaniesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CompaniesViewHolder, position: Int) {
        val item = differ.currentList.get(position)
        holder.bind(item)
    }



    private val companyDU=object:DiffUtil.ItemCallback<ProductionCompanyModel>(){
        override fun areItemsTheSame(
            oldItem: ProductionCompanyModel,
            newItem: ProductionCompanyModel,
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductionCompanyModel,
            newItem: ProductionCompanyModel,
        ): Boolean {
           return oldItem==newItem
        }

    }

    private val differ=AsyncListDiffer(this,companyDU)

    fun updateList(list:List<ProductionCompanyModel>){
        differ.submitList(list)
    }

}