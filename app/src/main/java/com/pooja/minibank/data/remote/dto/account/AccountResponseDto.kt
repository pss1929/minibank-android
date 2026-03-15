package com.pooja.minibank.data.remote.dto.account

data class AccountResponseDto(
    val balance: Double?,
    val currency: String?,
    val id: String?,
    val maskedNumber: String?,
    val name: String?,
    val updatedAt: String?
)