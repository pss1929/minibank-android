package com.pooja.minibank.core.utils

import android.content.Context
import androidx.biometric.BiometricManager

object Constants {
    const val SECURE_PREF_NAME ="secure_min_bank_pref"


    //onboarding shared pref keys
    const val SP_ONBOARDING_DONE = "onboarding_done"
    const val SP_ACCESS_TOKEN = "access_token"
    const val SP_REFRESH_TOKEN = "refresh_token"
    const val SP_IS_LOGGED_IN = "isLoggedIn"

    const val SP_EXPIRY_TIME = "expiryTime"
    const val SP_USERNAME = "username"

    const val SP_MOBILE = "mobile_number"


    fun getName(username : String) : String
    {
        return username.replace(".", " ")
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar {  s-> s.uppercase() }  }
    }

    fun getInitialLetter(name : String) : String
    {
        return name.split(" ").joinToString("") { it.first().uppercase() }
    }


     fun isBiometricAvailable(context : Context): Boolean {

        val biometricManager = BiometricManager.from(context)

        return when (
            biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        ) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }
}