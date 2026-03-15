package com.pooja.minibank.data.repository

import android.util.Log
import com.pooja.minibank.data.local.dao.TransactionDao
import com.pooja.minibank.data.mapper.toDomain
import com.pooja.minibank.data.mapper.toEntity
import com.pooja.minibank.data.remote.ApiService
import com.pooja.minibank.domain.model.transaction.Transaction
import com.pooja.minibank.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val transactionDao: TransactionDao
) : TransactionRepository{
    override suspend fun getTransactions(
        accountId: String,
        from: String,
        to: String,
        page: Int
    ): Pair<List<Transaction>, Int?> {

        return try {
            val response = apiService.getTransaction(accountId,from,to,page,20)

            if(response.isSuccessful)
            {
                val body = response.body()
                Log.d("API_RESPONSE", body.toString())

                val entities = body?.items?.map {
                    it.toEntity()
                } ?:emptyList()

                transactionDao.insertTransaction(entities)

                Pair(
                    entities.map { it.toDomain() }, body?.nextPage
                )
            }
            else{

                Pair(
                    transactionDao.getTransactionsById(accountId)
                        .map { it.toDomain() },null
                )

            }
        }
        catch (e: Exception)
        {
            Pair(
                transactionDao.getTransactionsById(accountId)
                    .map { it.toDomain() },
                null
            )
        }
    }

}