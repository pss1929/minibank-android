package com.pooja.minibank.domain.usecase.account

import com.pooja.minibank.domain.model.account.Account
import com.pooja.minibank.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() : List<Account>
    {
        return repository.getAccount()
    }
}