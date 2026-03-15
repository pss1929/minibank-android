package com.pooja.minibank.data.remote.interceptor

import com.pooja.minibank.data.local.pref.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SessionInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        if(response.code == 401)
        {
            tokenManager.clearSession()
            SessionManager.logout()
        }

        return response
    }
}