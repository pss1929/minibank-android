package com.pooja.minibank.data.remote.dto.transfer

data class TransferRequestDto(
    val fromAccountId: String,
    val beneficiaryId: String,
    val amount: Double,
    val currency: String,
    val note: String?
)