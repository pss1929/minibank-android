package com.pooja.minibank.domain.repository

import com.pooja.minibank.domain.model.account.Account

interface AccountRepository {

    suspend fun getAccount() : List<Account>
}