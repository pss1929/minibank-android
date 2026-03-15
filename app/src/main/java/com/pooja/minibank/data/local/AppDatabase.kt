package com.pooja.minibank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pooja.minibank.data.local.dao.AccountDao
import com.pooja.minibank.data.local.enity.AccountEntity

@Database(entities = [AccountEntity::class], version = 1)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}