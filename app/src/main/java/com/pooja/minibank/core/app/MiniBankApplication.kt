package com.pooja.minibank.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MiniBankApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}