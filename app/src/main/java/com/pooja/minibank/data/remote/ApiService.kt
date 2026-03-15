package com.pooja.minibank.data.remote

import com.pooja.minibank.data.remote.dto.account.AccountResponseDto
import com.pooja.minibank.data.remote.dto.login.LoginRequestDto
import com.pooja.minibank.data.remote.dto.login.LoginResponseDto
import com.pooja.minibank.data.remote.dto.otp.VerifyOtpRequestDto
import com.pooja.minibank.data.remote.dto.otp.VerifyOtpResponseDto
import com.pooja.minibank.data.remote.dto.transaction.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/auth/login")
    suspend  fun login(@Body request : LoginRequestDto) : Response<LoginResponseDto>

    @POST ("/auth/otp/verify")
    suspend fun verifyOtp(@Body request : VerifyOtpRequestDto) : Response<VerifyOtpResponseDto>

    @GET("/accounts")
    suspend fun getAccounts() : Response<List<AccountResponseDto>>

    @GET("accounts/{accountId}/transactions")
    suspend fun getTransaction(
        @Path("accountId") accountId : String,
        @Query("from") from : String,
        @Query("to") to : String,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int ) : Response<TransactionResponse>
}