package com.pooja.minibank.domain.model.transfer

data class Beneficiary(
    val id: String,
    val name: String,
    val accountMasked: String,
    val bank: String
)