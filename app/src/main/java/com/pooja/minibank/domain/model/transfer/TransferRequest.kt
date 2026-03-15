package com.pooja.minibank.domain.model.transfer

data class TransferRequest(
    val fromAccountId: String,
    val beneficiaryId: String,
    val amount: Double,
    val currency: String,
    val note: String?
)