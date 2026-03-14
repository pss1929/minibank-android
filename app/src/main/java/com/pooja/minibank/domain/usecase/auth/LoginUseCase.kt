package com.pooja.minibank.domain.usecase.auth

import com.pooja.minibank.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase@Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(username: String, password : String) = repository.login(username,password)
}