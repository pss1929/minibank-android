package com.pooja.minibank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pooja.minibank.data.local.enity.BeneficiaryEntity

@Dao
interface BeneficiaryDao {

    @Query("SELECT * FROM beneficiaries")
    suspend fun getBeneficiaries(): List<BeneficiaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<BeneficiaryEntity>)
}