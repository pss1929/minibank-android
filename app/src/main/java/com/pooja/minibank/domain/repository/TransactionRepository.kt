package com.pooja.minibank.domain.repository

import com.pooja.minibank.domain.model.transaction.Transaction

interface TransactionRepository {

    suspend fun getTransactions(
        accountId : String,
        from :String,
        to: String,
        page : Int,
    ) : Pair<List<Transaction>,Int?>
}