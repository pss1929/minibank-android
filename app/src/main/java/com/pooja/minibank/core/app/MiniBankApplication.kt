package com.pooja.minibank.core.app

import android.app.Application
import com.pooja.minibank.core.mock.BankingMockDispatcher
import dagger.hilt.android.HiltAndroidApp
import okhttp3.mockwebserver.MockWebServer

@HiltAndroidApp
class MiniBankApplication : Application(){

    companion object{
        lateinit var mockServer : MockWebServer
        lateinit var  BASE_URL : String
                private set
    }
    override fun onCreate() {
        super.onCreate()

        val latch = java.util.concurrent.CountDownLatch(1)

        Thread{
            mockServer = MockWebServer()
            mockServer.dispatcher = BankingMockDispatcher()
            mockServer.start(8000)

            BASE_URL = mockServer.url("/").toString()
            latch.countDown()
        }.start()

        latch.await()
    }
}