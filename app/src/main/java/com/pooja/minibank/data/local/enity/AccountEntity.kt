package com.pooja.minibank.data.local.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountEntity(

    @PrimaryKey
    val id : String,
    val name: String?,
    val maskedNumber: String?,
    val currency: String?,
    val balance: Double?,
    val updatedAt: String?
)