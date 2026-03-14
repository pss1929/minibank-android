package com.pooja.minibank.domain.model.request

data class LoginRequest(
    val password: String,
    val username: String
)