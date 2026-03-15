package com.pooja.minibank.domain.repository

import com.pooja.minibank.domain.model.transfer.Beneficiary
import com.pooja.minibank.domain.model.transfer.TransferRequest
import com.pooja.minibank.domain.model.transfer.TransferResult

interface TransferRepository {

    suspend fun getBeneficiaries(): List<Beneficiary>

    suspend fun transferMoney(
        token: String,
        idempotencyKey: String,
        request: TransferRequest
    ): TransferResult
}