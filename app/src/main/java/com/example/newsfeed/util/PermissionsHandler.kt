package com.example.newsfeed.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object PermissionsHandler {
    /*
   Must prepare for all situations, so this function will serve the purpose of
   checking the internet availability.

   Pretty generic android code. Can reuse method for other projects also.
    */
    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as? ConnectivityManager ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        ) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
