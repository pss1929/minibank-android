package com.pooja.minibank.domain.model.transfer

data class TransferResult(
    val transferId: String?,
    val status: String,
    val referenceNumber: String?,
    val message: String?
)