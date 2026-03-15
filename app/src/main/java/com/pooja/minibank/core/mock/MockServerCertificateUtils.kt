package com.pooja.minibank.core.mock

import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.HeldCertificate
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object MockServerCertificateUtils {

    data class SslComponent(
        val sslContext : SSLContext,
        val trustManager : X509TrustManager,
        val keyStore : KeyStore
    )

    fun createSelfSignedCert() : SslComponent{

        val heldCertificate = HeldCertificate.Builder()
            .addSubjectAlternativeName("localhost")
            .addSubjectAlternativeName("127.0.0.1")
            .commonName("MiniBank Mock Server")
            .validityInterval(
                System.currentTimeMillis(),
                System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 3 * 365
            ).build()

        val handShakeCertificate = HandshakeCertificates.Builder()
            .heldCertificate(heldCertificate)
            .addTrustedCertificate(heldCertificate.certificate)
            .build()

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).also { ks->
            ks.load(null,null)
            ks.setCertificateEntry("mock", heldCertificate.certificate)
        }


        return SslComponent(
            sslContext = handShakeCertificate.sslContext(),
            trustManager = handShakeCertificate.trustManager,
            keyStore = keyStore
        )

    }
}