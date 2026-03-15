package com.pooja.minibank.data.remote.dto.transaction

data class TransactionResponse(
    val items : List<TransactionDto>,
    val nextPage: Int?
)