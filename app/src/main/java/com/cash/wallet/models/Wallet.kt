package com.cash.wallet.models

data class Wallet(
    val address: String,
    val balance: Map<String, Double>,
    val transactions: List<Transaction>,
    val createdAt: Long,
    val lastUpdated: Long
)

data class WalletBalance(
    val currency: String,
    val amount: Double,
    val usdValue: Double
)
