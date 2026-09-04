package com.cash.wallet.models

data class User(
    val id: String,
    val username: String,
    val email: String,
    val phone: String,
    val walletAddress: String,
    val createdAt: Long,
    val isVerified: Boolean = false,
    val kycLevel: KYCLevel = KYCLevel.BASIC
)

enum class KYCLevel {
    BASIC,
    VERIFIED,
    PREMIUM,
    INSTITUTIONAL
}
