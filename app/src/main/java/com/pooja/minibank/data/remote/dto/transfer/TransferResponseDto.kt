package com.pooja.minibank.data.remote.dto.transfer

data class TransferResponseDto(
    val transferId: String?,
    val status: String,
    val referenceNumber: String?,
    val processedAt: String?,
    val message: String?,
    val errorCode: String?
)