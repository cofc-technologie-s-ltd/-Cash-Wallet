package com.cash.wallet.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val hash: String,
    val from: String,
    val to: String,
    val amount: Double,
    val currency: String,
    val timestamp: Long,
    val status: String,
    val type: String,
    val fee: Double = 0.0,
    val confirmations: Int = 0,
    val note: String = ""
)

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey
    val address: String,
    val name: String,
    val balance: Double,
    val currency: String,
    val lastUpdated: Long,
    val isDefault: Boolean = false
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val phone: String,
    val walletAddress: String,
    val createdAt: Long,
    val isVerified: Boolean = false,
    val kycLevel: String = "BASIC"
)

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val price: Double,
    val change24h: Double,
    val volume: Double,
    val marketCap: Double,
    val lastUpdated: Long
)
