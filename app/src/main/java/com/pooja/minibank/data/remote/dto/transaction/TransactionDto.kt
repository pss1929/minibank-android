package com.pooja.minibank.data.remote.dto.transaction

data class TransactionDto(
    val id : String,
    val accountId : String,
    val type : String,
    val amount : Double,
    val currency: String,
    val counterparty : String,
    val timestamp : String,
    val status : String,
    val narration : String
)

