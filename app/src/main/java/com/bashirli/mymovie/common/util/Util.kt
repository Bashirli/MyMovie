package com.bashirli.mymovie.common.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.bashirli.mymovie.R
import com.bashirli.mymovie.databinding.LayoutFullScreenImageBinding
import okhttp3.ResponseBody
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import java.util.Locale

class CustomProgressBar(){
    companion object{
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val layout = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
            dialog.setContentView(layout)
            dialog.setCancelable(false)

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }
}


fun findExceptionMessage(errorBody: ResponseBody?): String {
    if (errorBody != null) {
        val errorObj = JSONObject(errorBody.charStream().readText())
        val errorMessage = errorObj.getString("status_message")
        return errorMessage
    } else {
        return "Error"
    }
}

fun findExceptionMessageList(errorBody: ResponseBody?):String{
    if (errorBody != null) {
        val errorObj = JSONObject(errorBody.charStream().readText())
        val errorArray = errorObj.getJSONArray("status_message")
        val errorMessage = errorArray.getString(0)
        return errorMessage
    } else {
        return "Error"
    }
}

fun Activity.showToast(
    message: String,
    toastEnum: ToastEnum,
) {
    MotionToast.createColorToast(
        this,
        title = when (toastEnum) {
            ToastEnum.ERROR -> {
                this.resources.getString(R.string.error)
            }

            ToastEnum.INFO -> {
                this.resources.getString(R.string.info)
            }

            ToastEnum.SUCCESS -> {
                this.resources.getString(R.string.success)
            }
        },
        message,
        style = when (toastEnum) {
            ToastEnum.ERROR -> {
                MotionToastStyle.ERROR
            }

            ToastEnum.INFO -> {
                MotionToastStyle.INFO
            }

            ToastEnum.SUCCESS -> {
                MotionToastStyle.SUCCESS
            }
        },
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.resistsansdisplay_bold)
    )
}

fun Activity.reset() {
    val packageManager: PackageManager = packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    val componentName = intent?.component
    val mainIntent: Intent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(mainIntent)
    this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun dataDateToNormalDate(date:String) : String{
    val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    val resultDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(date)!!
    return outputDateFormat.format(resultDate)
}




