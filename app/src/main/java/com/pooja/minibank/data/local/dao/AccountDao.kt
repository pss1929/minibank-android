package com.pooja.minibank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pooja.minibank.data.local.enity.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * from account")
    suspend fun getAccountList() : List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountEntity: List<AccountEntity>)

    @Query("DELETE FROM account")
    suspend fun deleteAccount()
}