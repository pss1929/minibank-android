package com.pooja.minibank.domain.usecase.auth

import com.pooja.minibank.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtpUseCase@Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(sessionId: String, otp : String) = repository.verifyOt(sessionId,otp)

}