package com.pooja.minibank.core.network

import com.pooja.minibank.core.app.MiniBankApplication
import com.pooja.minibank.data.local.pref.TokenManager
import com.pooja.minibank.data.remote.ApiService
import com.pooja.minibank.data.remote.TransferApi
import com.pooja.minibank.data.remote.interceptor.AuthInterceptor
import com.pooja.minibank.data.remote.interceptor.SessionInterceptor
import com.pooja.minibank.data.repository.AuthRepositoryImpl
import com.pooja.minibank.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.net.ssl.SSLContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager) : AuthInterceptor{
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideSessionInterceptor(tokenManager: TokenManager) : SessionInterceptor{
        return SessionInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideSslContext() : SSLContext{
        val context = SSLContext.getInstance("TLS")
        context.init(null, arrayOf(MiniBankApplication.trustManager),null)
        return context
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(sslContext: SSLContext,
                            authInterceptor: AuthInterceptor,
                            sessionInterceptor: SessionInterceptor) : OkHttpClient{


        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory,
                MiniBankApplication.trustManager
                )
            .hostnameVerifier { hostname, _ ->
                hostname == "localhost"
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(authInterceptor)
            .addInterceptor(sessionInterceptor)
            .build()

        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(MiniBankApplication.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService) : AuthRepository{

        return AuthRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideTransferApi(retrofit: Retrofit): TransferApi {

        return retrofit.create(TransferApi::class.java)
    }

}