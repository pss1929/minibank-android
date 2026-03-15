package com.pooja.minibank.data.local.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
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