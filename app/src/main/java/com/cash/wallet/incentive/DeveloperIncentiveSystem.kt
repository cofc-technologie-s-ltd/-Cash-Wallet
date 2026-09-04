package com.cash.wallet.incentive

import java.security.MessageDigest
import java.util.Date

/**
 * Autonomous Developer Incentive System
 * 
 * Rewards developers automatically:
 * - 100 CASH per hour of development
 * - 1,000 CASH per major update
 * - 5,000 CASH per security audit
 */
class DeveloperIncentiveSystem {
    
    companion object {
        const val RATE_PER_HOUR = 100.0  // CASH
        const val BONUS_MINOR = 100.0    // CASH
        const val BONUS_MAJOR = 1000.0   // CASH
        const val BONUS_CRITICAL = 2000.0 // CASH
        const val BONUS_SECURITY = 5000.0 // CASH
    }
    
    data class Contribution(
        val id: String,
        val developer: String,
        val hours: Double,
        val type: ContributionType,
        val reward: Double,
        val timestamp: Long,
        val status: String = "PENDING",
        val txHash: String = ""
    )
    
    enum class ContributionType {
        DEVELOPMENT, MINOR_UPDATE, MAJOR_UPDATE, CRITICAL_FIX, SECURITY_AUDIT, BUG_REPORT
    }
    
    private val contributions = mutableListOf<Contribution>()
    
    fun processContribution(
        developer: String,
        hours: Double,
        type: ContributionType
    ): Contribution {
        val reward = calculateReward(hours, type)
        val id = generateId()
        val timestamp = System.currentTimeMillis()
        val txHash = generateQuantumHash()
        
        val contribution = Contribution(
            id = id,
            developer = developer,
            hours = hours,
            type = type,
            reward = reward,
            timestamp = timestamp,
            status = "PROCESSED",
            txHash = txHash
        )
        
        contributions.add(contribution)
        return contribution
    }
    
    private fun calculateReward(hours: Double, type: ContributionType): Double {
        val base = hours * RATE_PER_HOUR
        val bonus = when (type) {
            ContributionType.DEVELOPMENT -> 0.0
            ContributionType.MINOR_UPDATE -> BONUS_MINOR
            ContributionType.MAJOR_UPDATE -> BONUS_MAJOR
            ContributionType.CRITICAL_FIX -> BONUS_CRITICAL
            ContributionType.SECURITY_AUDIT -> BONUS_SECURITY
            ContributionType.BUG_REPORT -> 10.0
        }
        return base + bonus
    }
    
    private fun generateId(): String {
        return "DEV_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}"
    }
    
    private fun generateQuantumHash(): String {
        val data = "${System.currentTimeMillis()}${Math.random()}"
        val digest = MessageDigest.getInstance("SHA-512")
        val hash = digest.digest(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }.take(64)
    }
    
    fun getContributions(developer: String): List<Contribution> {
        return contributions.filter { it.developer == developer }
    }
    
    fun getTotalRewards(developer: String): Double {
        return contributions.filter { it.developer == developer }.sumOf { it.reward }
    }
    
    fun getContributionSummary(): String {
        val totalDevs = contributions.map { it.developer }.distinct().size
        val totalRewards = contributions.sumOf { it.reward }
        val totalHours = contributions.sumOf { it.hours }
        
        return """
            ╔═══════════════════════════════════════════════════════════════╗
            ║          DEVELOPER INCENTIVE SYSTEM SUMMARY                 ║
            ╠═══════════════════════════════════════════════════════════════╣
            ║                                                             ║
            ║  TOTAL DEVELOPERS: $totalDevs                                ║
            ║  TOTAL CONTRIBUTIONS: ${contributions.size}                  ║
            ║  TOTAL HOURS: ${"%.1f".format(totalHours)}                  ║
            ║  TOTAL REWARDS: ${"%,.2f".format(totalRewards)} CASH         ║
            ║  USD VALUE: $${"%,.2f".format(totalRewards * 20)}            ║
            ║                                                             ║
            ╚═══════════════════════════════════════════════════════════════╝
        """.trimIndent()
    }
}
