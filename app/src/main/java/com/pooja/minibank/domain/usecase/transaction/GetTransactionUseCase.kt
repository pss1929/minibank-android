package com.pooja.minibank.domain.usecase.transaction

import com.pooja.minibank.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
){
    suspend operator fun invoke(
        accountId :String,
        from : String,
        to : String,
        page : Int
    ) = repository.getTransactions(accountId,from,to,page)
}