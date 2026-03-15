package com.pooja.minibank.data.remote.interceptor

import com.pooja.minibank.data.local.pref.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if(tokenManager.isTokenExpired())
        {
            tokenManager.clearSession()
            SessionManager.logout()
        }
       val token = tokenManager.getAccessToken()
        val request = chain.request().newBuilder()

        token.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }

}