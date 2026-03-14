package com.pooja.minibank.data.mapper

import com.pooja.minibank.data.remote.dto.login.LoginResponseDto
import com.pooja.minibank.data.remote.dto.otp.VerifyOtpResponseDto
import com.pooja.minibank.domain.model.response.LoginResponse
import com.pooja.minibank.domain.model.response.VerifyOtpResponse

fun LoginResponseDto.toDomain() : LoginResponse{
    return LoginResponse(
        sessionId = sessionId,
        otpRequired = otpRequired,
        maskedPhone = maskedPhone
    )
}

fun VerifyOtpResponseDto.toDomain() : VerifyOtpResponse{
    return VerifyOtpResponse(
        accessToken = accessToken,
        expiresIn = expiresIn,
        refreshToken = refreshToken
    )
}