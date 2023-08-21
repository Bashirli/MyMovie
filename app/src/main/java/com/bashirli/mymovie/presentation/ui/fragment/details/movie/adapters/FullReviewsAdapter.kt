package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.R
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.ItemReviewBinding
import com.bashirli.mymovie.domain.model.details.reviews.ResultModel

class FullReviewsAdapter : RecyclerView.Adapter<FullReviewsAdapter.FullReviewViewHolder>() {



    inner class FullReviewViewHolder(private val binding:ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:ResultModel){
            binding.resultModel=item
            binding.executePendingBindings()
        }

        fun setUI(){
            expandingCart(binding)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullReviewViewHolder {
        val layout=ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FullReviewViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FullReviewViewHolder, position: Int) {
        val item=differ.currentList.get(position)
        holder.bind(item)
        holder.setUI()

    }

    private val reviewDU=object:DiffUtil.ItemCallback<ResultModel>(){
        override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ=AsyncListDiffer(this,reviewDU)

    fun updateList(list:List<ResultModel>){
            differ.submitList(list)
    }

    private fun expandingCart(binding:ItemReviewBinding){
        var isExpanded=false
        binding.apply {
            textReview.post {
                val lineCount=textReview.layout.lineCount
                if(lineCount>5){
                    textReview.ellipsize=TextUtils.TruncateAt.END
                    textReview.maxLines=5
                    buttonExpand.visible()
                    buttonExpand.setOnClickListener {
                        if(isExpanded){
                            buttonExpand.setIconResource(R.drawable.baseline_keyboard_arrow_down_24)
                            textReview.maxLines=5
                        }else{
                            buttonExpand.setIconResource(R.drawable.baseline_keyboard_arrow_up_24)
                            textReview.maxLines=lineCount
                        }
                        isExpanded=!isExpanded

                    }
                }
            }
        }
    }

}