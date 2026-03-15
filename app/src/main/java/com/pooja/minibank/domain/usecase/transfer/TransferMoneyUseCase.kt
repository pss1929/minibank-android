package com.pooja.minibank.domain.usecase.transfer

import com.pooja.minibank.domain.model.transfer.TransferRequest
import com.pooja.minibank.domain.model.transfer.TransferResult
import com.pooja.minibank.domain.repository.TransferRepository
import java.util.UUID
import javax.inject.Inject


class TransferMoneyUseCase @Inject constructor(
    private val repository: TransferRepository
) {

    suspend operator fun invoke(
        token: String,
        request: TransferRequest
    ): TransferResult {

        val key = UUID.randomUUID().toString()

        return repository.transferMoney(token, key, request)
    }
}