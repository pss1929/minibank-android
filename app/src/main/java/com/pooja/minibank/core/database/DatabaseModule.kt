package com.pooja.minibank.core.database

import android.content.Context
import androidx.room.Room
import com.pooja.minibank.data.local.AppDatabase
import com.pooja.minibank.data.local.dao.AccountDao
import com.pooja.minibank.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : AppDatabase
    {
        val db = Room.databaseBuilder(context, name = "mini_db", klass = AppDatabase::class.java).build()
         return db
    }

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDatabase) : AccountDao{
        return db.accountDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db : AppDatabase) : TransactionDao{
        return  db.transactionDao()
    }
}