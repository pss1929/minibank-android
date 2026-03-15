package com.pooja.minibank.data.remote.dto.transfer

data  class BeneficiaryDto (
    val id: String,
    val name: String,
    val accountMasked: String,
    val bank: String
)