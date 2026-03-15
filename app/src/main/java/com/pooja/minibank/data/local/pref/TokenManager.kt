package com.pooja.minibank.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import com.pooja.minibank.core.utils.Constants
import javax.inject.Inject

class TokenManager @Inject constructor(val pref : SharedPreferences) {

    // Access Token
    fun saveAccessToken(token : String)
    {
        pref.edit { putString(Constants.SP_ACCESS_TOKEN,token) }
    }

    fun getAccessToken()
    {
        pref.getString(Constants.SP_ACCESS_TOKEN, null)
    }

    // Refresh Token
    fun saveRefreshToken(token : String)
    {
        pref.edit { putString(Constants.SP_REFRESH_TOKEN,token) }
    }

    fun getRefreshToken()
    {
        pref.getString(Constants.SP_REFRESH_TOKEN, null)
    }

    // Expiry In
    fun saveExpiryIn(expiryIn : Long)
    {
        pref.edit { putLong(Constants.SP_EXPIRY_TIME, expiryIn) }
    }

    fun getExpiryIn()
    {
        pref.getLong(Constants.SP_EXPIRY_TIME,0)
    }

    //Check token expired or nit
    fun isTokenExpired() : Boolean
    {
        val expiryTime  = pref.getLong(Constants.SP_EXPIRY_TIME,0)
        val  isExpired = System.currentTimeMillis()>expiryTime
        return isExpired
    }

    //Clear all
    fun clearAll()
    {
        pref.edit { clearAll() }
    }

    fun clearSession()
    {
        pref.edit { clear() }
    }

    fun isLoggedIn() : Boolean{
        return getAccessToken()!=null
    }
}