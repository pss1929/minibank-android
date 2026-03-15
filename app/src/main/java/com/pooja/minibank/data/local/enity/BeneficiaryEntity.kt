package com.pooja.minibank.data.local.enity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "beneficiaries")
data class BeneficiaryEntity(

    @PrimaryKey
    val id: String,

    val name: String,
    val accountMasked: String,
    val bank: String
)