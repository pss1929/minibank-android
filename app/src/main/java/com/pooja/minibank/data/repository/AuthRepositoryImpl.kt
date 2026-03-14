package com.pooja.minibank.data.repository

import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.data.mapper.toDomain
import com.pooja.minibank.data.remote.ApiService
import com.pooja.minibank.data.remote.BaseApicCall
import com.pooja.minibank.data.remote.dto.login.LoginRequestDto
import com.pooja.minibank.data.remote.dto.otp.VerifyOtpRequestDto
import com.pooja.minibank.domain.model.response.LoginResponse
import com.pooja.minibank.domain.model.response.VerifyOtpResponse
import com.pooja.minibank.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService : ApiService) : AuthRepository {

    // Login API
    override suspend fun login(username: String, password: String
    ): ResponseState<LoginResponse> {

        val response =  BaseApicCall.safeApiCall { apiService.login(LoginRequestDto(username, password)) }

        return when (response) {

            is ResponseState.Success -> {

                ResponseState.Success(response.data.toDomain())
            }

            is ResponseState.Error -> response

            is ResponseState.Loading -> response
        } as ResponseState<LoginResponse>
    }

    override suspend fun verifyOt(
        sessionId: String,
        otp: String
    ): ResponseState<VerifyOtpResponse> {

        val response =  BaseApicCall.safeApiCall { apiService.verifyOtp(VerifyOtpRequestDto(otp, sessionId)) }

        return when (response) {

            is ResponseState.Success -> {

                ResponseState.Success(response.data.toDomain())
            }

            is ResponseState.Error -> response

            is ResponseState.Loading -> response
        } as ResponseState<VerifyOtpResponse>
    }
}