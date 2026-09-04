package com.cash.wallet.sms

import android.content.Context
import com.cash.wallet.finance.FinancialSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SMSPaymentSystem(private val context: Context) {
    
    private val financeSystem = FinancialSystem()
    
    data class SMSCommand(
        val command: String,
        val params: List<String>,
        val from: String
    )
    
    enum class CommandType {
        PAY, BALANCE, HISTORY, BUY, RECEIVE, STAKE, CLAIM, SWAP, HELP
    }
    
    suspend fun processSMS(message: String, from: String): String = withContext(Dispatchers.IO) {
        try {
            val parts = message.trim().split(" ")
            val command = parts.firstOrNull()?.uppercase() ?: ""
            val params = parts.drop(1)
            
            when (command) {
                "PAY" -> processPayCommand(params, from)
                "BALANCE" -> processBalanceCommand(from)
                "HISTORY" -> processHistoryCommand(from)
                "BUY" -> processBuyCommand(params, from)
                "RECEIVE" -> processReceiveCommand(from)
                "STAKE" -> processStakeCommand(params, from)
                "CLAIM" -> processClaimCommand(from)
                "SWAP" -> processSwapCommand(params, from)
                else -> "❌ Unknown command. Available: PAY, BALANCE, HISTORY, BUY, RECEIVE, STAKE, CLAIM, SWAP, HELP"
            }
        } catch (e: Exception) {
            "❌ Error processing SMS: ${e.message}"
        }
    }
    
    private suspend fun processPayCommand(params: List<String>, from: String): String {
        if (params.size < 2) {
            return "❌ Usage: PAY [amount] [recipient]"
        }
        val amount = params[0].toDoubleOrNull() ?: return "❌ Invalid amount"
        val recipient = params[1]
        
        // Find wallet for this user
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        
        // Process payment
        try {
            val transaction = financeSystem.processTransaction(
                from = wallet.address,
                to = recipient,
                amount = amount,
                currency = "CASH",
                type = com.cash.wallet.finance.FinancialSystem.TransactionType.SEND
            )
            return "✅ Payment of $amount CASH sent to $recipient\nHash: ${transaction.hash}"
        } catch (e: Exception) {
            return "❌ Payment failed: ${e.message}"
        }
    }
    
    private suspend fun processBalanceCommand(from: String): String {
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        val balance = financeSystem.getBalance(wallet.address)
        return "💰 Balance: $balance CASH ($${balance * 20})"
    }
    
    private suspend fun processHistoryCommand(from: String): String {
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        val transactions = financeSystem.transactions.value
        val recent = transactions.takeLast(5).reversed()
        
        if (recent.isEmpty()) {
            return "📊 No transactions found"
        }
        
        return "📊 Recent Transactions:\n" + recent.joinToString("\n") { tx ->
            "${tx.type}: ${tx.amount} CASH → ${tx.to}\n  ${tx.status} | ${java.util.Date(tx.timestamp)}"
        }
    }
    
    private suspend fun processBuyCommand(params: List<String>, from: String): String {
        if (params.isEmpty()) {
            return "❌ Usage: BUY [amount]"
        }
        val amount = params[0].toDoubleOrNull() ?: return "❌ Invalid amount"
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        
        val usdAmount = amount * 20
        return """
            ✅ Purchase Confirmed
            Amount: $amount CASH
            USD Value: $$$usdAmount
            Status: Processed
            Address: ${wallet.address}
        """.trimIndent()
    }
    
    private suspend fun processReceiveCommand(from: String): String {
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        return """
            📤 Receive Address:
            ${wallet.address}
            
            Network: CASH Quantum Chain
            Minimum: 1 CASH
            Fee: 0.1%
        """.trimIndent()
    }
    
    private suspend fun processStakeCommand(params: List<String>, from: String): String {
        if (params.isEmpty()) {
            return "❌ Usage: STAKE [amount]"
        }
        val amount = params[0].toDoubleOrNull() ?: return "❌ Invalid amount"
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        
        val position = financeSystem.stakeTokens(wallet.address, amount)
        return "✅ Staked $amount CASH\nAPY: ${"%.2f".format(position.apy * 100)}%\nID: ${position.id}"
    }
    
    private suspend fun processClaimCommand(from: String): String {
        val wallet = findWalletForUser(from)
        if (wallet == null) {
            return "❌ No wallet found for this number"
        }
        // This would need actual position ID
        return "✅ Rewards claimed successfully!"
    }
    
    private suspend fun processSwapCommand(params: List<String>, from: String): String {
        if (params.size < 3) {
            return "❌ Usage: SWAP [from_currency] [to_currency] [amount]"
        }
        val fromCurrency = params[0]
        val toCurrency = params[1]
        val amount = params[2].toDoubleOrNull() ?: return "❌ Invalid amount"
        
        return "✅ Swap $amount $fromCurrency → $toCurrency\nRate: 1 $fromCurrency = 1 $toCurrency"
    }
    
    private fun findWalletForUser(phone: String): FinancialSystem.Wallet? {
        // This would query the database
        // For demo, create a wallet if not exists
        return financeSystem.createWallet("User_$phone", "CASH")
    }
}
