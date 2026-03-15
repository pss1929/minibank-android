package com.pooja.minibank.data.repository

import android.net.http.HttpException
import com.pooja.minibank.data.local.dao.BeneficiaryDao
import com.pooja.minibank.data.local.enity.BeneficiaryEntity
import com.pooja.minibank.data.remote.TransferApi
import com.pooja.minibank.data.remote.dto.transfer.TransferRequestDto
import com.pooja.minibank.domain.model.transfer.Beneficiary
import com.pooja.minibank.domain.model.transfer.TransferRequest
import com.pooja.minibank.domain.model.transfer.TransferResult
import com.pooja.minibank.domain.repository.TransferRepository
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val api: TransferApi,
    private val dao: BeneficiaryDao
) : TransferRepository {

    override suspend fun getBeneficiaries(): List<Beneficiary> {

        return try {

            val response = api.getBeneficiaries()

            dao.insertAll(
                response.map {
                    BeneficiaryEntity(
                        it.id,
                        it.name,
                        it.accountMasked,
                        it.bank
                    )
                }
            )

            response.map {
                Beneficiary(
                    it.id,
                    it.name,
                    it.accountMasked,
                    it.bank
                )
            }

        } catch (e: Exception) {

            dao.getBeneficiaries().map {
                Beneficiary(
                    it.id,
                    it.name,
                    it.accountMasked,
                    it.bank
                )
            }
        }
    }

    override suspend fun transferMoney(
        token: String,
        idempotencyKey: String,
        request: TransferRequest
    ): TransferResult {

        return try {

            val response = api.transferMoney(
                "Bearer $token",
                idempotencyKey,
                TransferRequestDto(
                    request.fromAccountId,
                    request.beneficiaryId,
                    request.amount,
                    request.currency,
                    request.note
                )
            )

            TransferResult(
                transferId = response.transferId,
                status = response.status,
                referenceNumber = response.referenceNumber,
                message = response.message
            )

        } catch (e: HttpException) {

            TransferResult(
                transferId = null,
                status = "FAILED",
                referenceNumber = null,
                message = "Unauthorized (401). Check token."
            )

        } catch (e: Exception) {

            TransferResult(
                transferId = null,
                status = "FAILED",
                referenceNumber = null,
                message = "Network error"
            )
        }
    }
}