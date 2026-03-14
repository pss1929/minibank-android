package com.pooja.minibank.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.CloudMediaProviderContract

object NetworkUtil {

    fun isNetworkAvailable(context : Context): Boolean {
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectManager.activeNetwork?: return false
        val capability = connectManager.getNetworkCapabilities(network)
        return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)==true
    }
}