package com.bashirli.mymovie.common.util

import android.app.Activity
import android.content.Context
import android.transition.TransitionInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bashirli.mymovie.R

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageURL(url:String,context: Context){
    val options=RequestOptions.placeholderOf(placeHolder(context))
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun ImageView.setImageMovieURL(url:String,context: Context){
    val options=RequestOptions.placeholderOf(placeHolder(context))
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context).setDefaultRequestOptions(options).load(BASE_IMAGE_URL+url).into(this)
}



private fun placeHolder(context:Context):CircularProgressDrawable{
    val circularProgressDrawable=CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth=8f
    circularProgressDrawable.centerRadius=40f
    circularProgressDrawable.setTint(context.getColor(R.color.blue))
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun View.visible(){
    this.visibility=View.VISIBLE
}

fun View.invisible(){
    this.visibility=View.INVISIBLE
}

fun View.gone(){
    this.visibility=View.GONE
}

fun Fragment.enableTransitionAnim(){
    val anim= TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    this.sharedElementEnterTransition=anim
    sharedElementReturnTransition=anim
}