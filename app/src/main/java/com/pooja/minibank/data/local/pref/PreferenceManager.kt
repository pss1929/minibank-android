package com.pooja.minibank.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferenceManager @Inject constructor(val pref : SharedPreferences){


    fun addPref(key : String, value: String)
    {
        pref.edit { putString(key,value) }
    }

    fun addPref(key: String, value: Boolean)
    {
        pref.edit { putBoolean(key,value) }
    }

    fun addPref(key: String, value : Int)
    {
        pref.edit { putInt(key,value) }
    }

    fun getStringPref(key :String) : String?
    {
        return pref.getString(key, null)
    }

    fun getBooleanPref(key : String) : Boolean{
        return pref.getBoolean(key,false)
    }

    fun getIntPref(key: String) : Int{
        return pref.getInt(key,0)
    }

    fun clearKey(key: String)
    {
        pref.edit { remove(key) }
    }

    fun clearAll()
    {
        pref.edit { clearAll() }
    }
}