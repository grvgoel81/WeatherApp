package com.gaurav.gojekassignment.util

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

class CheckInternetConnection(private val context: Context) : AppCompatActivity() {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }
}