package com.cash.wallet.models

data class Transaction(
    val id: String,
    val from: String,
    val to: String,
    val amount: Double,
    val currency: String,
    val timestamp: Long,
    val status: TransactionStatus,
    val type: TransactionType,
    val hash: String? = null,
    val note: String? = null
)

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}

enum class TransactionType {
    SENT,
    RECEIVED,
    SWAP,
    BUY,
    SELL
}
