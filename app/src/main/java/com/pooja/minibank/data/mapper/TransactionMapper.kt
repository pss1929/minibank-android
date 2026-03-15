package com.pooja.minibank.data.mapper

import com.pooja.minibank.data.local.enity.TransactionEntity
import com.pooja.minibank.data.remote.dto.transaction.TransactionDto
import com.pooja.minibank.domain.model.transaction.Transaction

fun TransactionDto.toEntity() = TransactionEntity(
    id,
    accountId,
    type,
    amount,
    currency,
    counterparty,
    timestamp,
    status,
    narration
)

fun TransactionEntity.toDomain() = Transaction(
    id,
    accountId,
    type,
    amount,
    currency,
    counterparty,
    timestamp,
    status,
    narration
)