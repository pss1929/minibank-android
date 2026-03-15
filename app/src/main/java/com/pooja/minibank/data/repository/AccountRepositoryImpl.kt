package com.pooja.minibank.data.repository

import com.pooja.minibank.data.local.dao.AccountDao
import com.pooja.minibank.data.mapper.toDomain
import com.pooja.minibank.data.mapper.toEntity
import com.pooja.minibank.data.remote.ApiService
import com.pooja.minibank.domain.model.account.Account
import com.pooja.minibank.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accountDao: AccountDao
) : AccountRepository{
    override suspend fun getAccount(): List<Account> {

        return try {
            val response = apiService.getAccounts()

            if(response.isSuccessful)
            {
                val accounts = response.body()?: emptyList()
                val entities = accounts.map { it.toEntity() }
                accountDao.insertAccount(entities)
                entities.map {
                    it.toDomain()
                }
            }
            else{
                accountDao.getAccountList().map { it.toDomain() }
            }

        }
        catch (e: Exception)
        {
            accountDao.getAccountList().map { it.toDomain() }
        }
    }


}