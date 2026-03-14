package com.pooja.minibank.domain.repository

import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.domain.model.response.LoginResponse
import com.pooja.minibank.domain.model.response.VerifyOtpResponse

interface AuthRepository {

    suspend fun login(username: String, password : String) : ResponseState<LoginResponse>

    suspend fun verifyOt(sessionId: String, otp : String) : ResponseState<VerifyOtpResponse>

}