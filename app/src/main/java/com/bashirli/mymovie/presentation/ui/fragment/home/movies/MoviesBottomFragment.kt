package com.bashirli.mymovie.presentation.ui.fragment.home.movies

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bashirli.mymovie.R
import com.bashirli.mymovie.databinding.LayoutBottomMoviesBinding
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MoviesBottomFragment : BottomSheetDialogFragment() {

    companion object{
        fun instance(data:ResultModel) : MoviesBottomFragment {
            val bundle=Bundle()
            bundle.remove("data")
            bundle.putParcelable("data",data)
            return MoviesBottomFragment().apply {
               arguments = bundle
            }
        }
    }

    private var resultModel: ResultModel?=null

    private var _binding: LayoutBottomMoviesBinding?=null
    val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= LayoutBottomMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


    private fun setup(){
        resultModel=arguments?.getParcelable("data")
        binding.movieData=resultModel


    }

}