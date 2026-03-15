package com.pooja.minibank.domain.usecase.transfer

import com.pooja.minibank.domain.repository.TransferRepository
import javax.inject.Inject

class GetBeneficiariesUseCase @Inject constructor(
    private val repository: TransferRepository
) {
    suspend operator fun invoke() =
        repository.getBeneficiaries()
}