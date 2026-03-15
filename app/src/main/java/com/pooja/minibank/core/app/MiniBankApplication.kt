package com.pooja.minibank.core.app

import android.app.Application
import com.pooja.minibank.core.mock.BankingMockDispatcher
import com.pooja.minibank.core.mock.MockServerCertificateUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.mockwebserver.MockWebServer
import javax.net.ssl.X509TrustManager

@HiltAndroidApp
class MiniBankApplication : Application(){

    companion object{
        lateinit var mockServer : MockWebServer
        lateinit var  BASE_URL : String
                private set

        lateinit var trustManager : X509TrustManager
            private set

    }

    override fun onCreate() {
        super.onCreate()



       // SessionManager.init(this)
        val latch = java.util.concurrent.CountDownLatch(1)

        Thread{
            val sslComponents = MockServerCertificateUtils.createSelfSignedCert()
            trustManager = sslComponents.trustManager

            mockServer = MockWebServer()
            mockServer.useHttps(sslComponents.sslContext.socketFactory, false)

            mockServer.dispatcher = BankingMockDispatcher()
            mockServer.start(8443)

            BASE_URL = mockServer.url("/").toString()
            latch.countDown()
        }.start()

        latch.await()
    }
}