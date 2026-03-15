package com.pooja.minibank.data.remote

import com.pooja.minibank.data.remote.dto.transfer.BeneficiaryDto
import com.pooja.minibank.data.remote.dto.transfer.TransferRequestDto
import com.pooja.minibank.data.remote.dto.transfer.TransferResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TransferApi {

    @GET("beneficiaries")
    suspend fun getBeneficiaries() : List<BeneficiaryDto>

    @POST("transfers")
    suspend fun transferMoney(
        @Header("Authorization") token :String,
        @Header("Idempotency-Key") idempotencyKey :String,
        @Body request : TransferRequestDto
        ) : TransferResponseDto
}