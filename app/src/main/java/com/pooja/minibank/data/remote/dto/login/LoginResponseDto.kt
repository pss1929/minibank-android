package com.pooja.minibank.data.remote.dto.login

data class LoginResponseDto(
    val sessionId : String?,
    val otpRequired : Boolean?,
    val maskedPhone : String?
)