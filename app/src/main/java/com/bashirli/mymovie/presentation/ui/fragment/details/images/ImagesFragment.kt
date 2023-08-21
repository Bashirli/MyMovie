package com.bashirli.mymovie.presentation.ui.fragment.details.images

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bashirli.mymovie.common.base.BaseFragment
import com.bashirli.mymovie.common.util.setImageMovieURL
import com.bashirli.mymovie.databinding.FragmentImagesBinding
import com.bashirli.mymovie.databinding.LayoutFullScreenImageBinding
import com.bashirli.mymovie.presentation.ui.fragment.details.images.ImagesFragmentArgs
import com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters.ImageGridAdapter

class ImagesFragment : BaseFragment<FragmentImagesBinding>(FragmentImagesBinding::inflate) {

    private val adapter= ImageGridAdapter()
    private val args by navArgs<ImagesFragmentArgs>()

    override fun onViewCreateFinished() {
        val list=args.imagesModel.backdrops
        binding.rvPhotos.adapter=adapter
        adapter.updateList(list)
    }

    override fun setup() {
        binding.apply {
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        adapter.onLongClickImageListener={
            showImageFullScreen(it.filePath,requireContext())
        }

    }

    private fun showImageFullScreen(url: String?, context: Context) {
        val dialog = Dialog(context)
        val bindingImage = LayoutFullScreenImageBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(bindingImage.root)
        dialog.setCancelable(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dialog.window!!.setBackgroundBlurRadius(100)
        }
        binding.constraintLayout.alpha=0.3f
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        bindingImage.imageView.setImageMovieURL(url ?: "", context)
        bindingImage.buttonGoBack.setOnClickListener {
            dialog.dismiss()
            binding.constraintLayout.alpha=1f
        }

        dialog.show()

    }



}