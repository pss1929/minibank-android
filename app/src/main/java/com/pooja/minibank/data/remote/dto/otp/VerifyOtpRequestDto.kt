package com.pooja.minibank.data.remote.dto.otp

data class VerifyOtpRequestDto(
    val otp: String?,
    val sessionId: String?
)