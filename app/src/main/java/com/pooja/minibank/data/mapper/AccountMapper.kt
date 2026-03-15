package com.pooja.minibank.data.mapper

import com.pooja.minibank.data.local.enity.AccountEntity
import com.pooja.minibank.data.remote.dto.account.AccountResponseDto
import com.pooja.minibank.domain.model.account.Account


fun AccountResponseDto.toEntity() = AccountEntity(
    id = id!!,
    name = name,
    maskedNumber = maskedNumber,
    currency = currency,
    balance = balance,
    updatedAt = updatedAt
)

fun AccountEntity.toDomain() = Account(

    id = id,
    name = name,
    maskedNumber = maskedNumber,
    currency = currency,
    balance = balance,
    updatedAt = updatedAt
)