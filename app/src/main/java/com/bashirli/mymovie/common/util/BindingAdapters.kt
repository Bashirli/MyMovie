package com.bashirli.mymovie.common.util

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.math.RoundingMode

object BindingAdapters {

    @BindingAdapter("app:setImageURL")
    @JvmStatic
    fun setImageURL(view:ImageView,url:String?){
        url?.let {
            view.setImageURL(it,view.context)
        }
    }


    @BindingAdapter("app:setImageMovieURL")
    @JvmStatic
    fun setImageMovieURL(view:ImageView,url:String?){
        url?.let {
            view.setImageMovieURL(it,view.context)
        }
    }

    @BindingAdapter("app:textRating")
    @JvmStatic
    fun textRating(view:TextView,text:String){
        val number = text.toDouble()
        val roundedNumber=number.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        view.text=roundedNumber.toString()
    }

    @BindingAdapter("app:textTime")
    @JvmStatic
    fun textTime(view:TextView,text:String){
        val timeText= dataDateToNormalDate(text)
        view.setText(timeText)
    }

}