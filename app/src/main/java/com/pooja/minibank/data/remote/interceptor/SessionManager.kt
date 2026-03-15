package com.pooja.minibank.data.remote.interceptor

object SessionManager {

    var logoutListener : (() -> Unit)? = null

    fun logout()
    {
        logoutListener?.invoke()
    }
}