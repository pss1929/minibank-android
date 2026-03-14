package com.pooja.minibank.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import com.pooja.minibank.core.utils.Constants
import javax.inject.Inject

class TokenManager @Inject constructor(val pref : SharedPreferences) {

    fun saveAccessToken(token : String)
    {
        pref.edit { putString(Constants.SP_ACCESS_TOKEN,token) }
    }

    fun getAccessToken()
    {
        pref.getString(Constants.SP_ACCESS_TOKEN, null)
    }

    fun saveRefreshToken(token : String)
    {
        pref.edit { putString(Constants.SP_REFRESH_TOKEN,token) }
    }

    fun getRefreshToken()
    {
        pref.getString(Constants.SP_REFRESH_TOKEN, null)
    }

    fun clearAll()
    {
        pref.edit { clearAll() }
    }
}