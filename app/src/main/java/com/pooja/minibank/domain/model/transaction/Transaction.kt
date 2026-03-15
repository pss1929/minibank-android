package com.pooja.minibank.domain.model.transaction


data class Transaction(
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