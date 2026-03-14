package com.pooja.minibank.domain.model.response


data class LoginResponse(
    val maskedPhone: String?,
    val otpRequired: Boolean?,
    val sessionId: String?
)