package com.pooja.minibank.data.remote.dto.otp

data class VerifyOtpResponseDto(
    val accessToken: String?,
    val expiresIn: Int?,
    val refreshToken: String?
)