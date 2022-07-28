package com.ayan.appniess

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object CheckInternet {
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        return if (netinfo != null && netinfo.isConnectedOrConnecting) {
            val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mobile != null && mobile.isConnectedOrConnecting || wifi != null && wifi.isConnectedOrConnecting) true else false
        } else false
    }
}