package com.bizmiz.gulbozor.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@RequiresApi(Build.VERSION_CODES.M)
fun Fragment.isHasPermission(permission: String): Boolean {
    return requireActivity().applicationContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }
fun Fragment.askPermission(permissions: Array<out String>, @IntRange(from = 0) requestCode: Int) =
    ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)

fun File.toRequestBody(): RequestBody =
    this.asRequestBody("image/png".toMediaTypeOrNull())

fun File.toMultiPart(key: String) =
    MultipartBody.Part.createFormData(key, this.name, this.toRequestBody())
fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}
fun networkCheck(context:Context): Boolean {
    val conManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val internetInfo = conManager.activeNetworkInfo

    return internetInfo != null && internetInfo.isConnected
}
fun checkMonth(number:Int):String{
    var month = ""
    when(number){
        1->{ month = "Yanvar"}
        2->{ month = "Fevral"}
        3->{ month = "Mart"}
        4->{ month = "Aprel"}
        5->{ month = "May"}
        6->{ month = "Iyun"}
        7->{ month = "Iyul"}
        8->{ month = "Avgust"}
        9->{ month = "Sentyabr"}
        10->{ month = "Oktyabr"}
        11->{ month = "Noyabr"}
        12->{ month = "Dekabr"}
    }
    return month
}