package com.pooja.minibank.domain.model.response

data class VerifyOtpResponse(
    val accessToken: String?,
    val expiresIn: Int?,
    val refreshToken: String?
)