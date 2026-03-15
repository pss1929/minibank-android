package com.pooja.minibank.domain.repository

import com.pooja.minibank.data.repository.AccountRepositoryImpl
import com.pooja.minibank.data.repository.TransactionRepositoryImpl
import com.pooja.minibank.data.repository.TransferRepositoryImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository


    @Binds
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    abstract fun bindTransferRepository(
        impl: TransferRepositoryImpl
    ): TransferRepository

}

