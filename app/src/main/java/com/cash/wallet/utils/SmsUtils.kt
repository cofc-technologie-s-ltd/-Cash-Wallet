package com.cash.wallet.utils

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast

object SmsUtils {
    private const val SMS_COMMAND_PAY = "PAY"
    private const val SMS_COMMAND_BALANCE = "BALANCE"
    private const val SMS_COMMAND_HISTORY = "HISTORY"
    private const val SMS_COMMAND_BUY = "BUY"

    fun parseSmsCommand(message: String): SmsCommand? {
        val parts = message.trim().split(" ")
        return when (parts.firstOrNull()?.uppercase()) {
            SMS_COMMAND_PAY -> {
                if (parts.size >= 3) {
                    val amount = parts[1].toDoubleOrNull()
                    val recipient = parts[2]
                    if (amount != null && recipient.isNotEmpty()) {
                        SmsCommand.Pay(amount, recipient)
                    } else null
                } else null
            }
            SMS_COMMAND_BALANCE -> SmsCommand.Balance
            SMS_COMMAND_HISTORY -> SmsCommand.History
            SMS_COMMAND_BUY -> {
                if (parts.size >= 2) {
                    val amount = parts[1].toDoubleOrNull()
                    if (amount != null) SmsCommand.Buy(amount) else null
                } else null
            }
            else -> null
        }
    }

    fun sendSms(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "✅ SMS Sent!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "❌ SMS Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendConfirmation(context: Context, phoneNumber: String, transactionId: String, amount: Double) {
        val message = """
            ✅ Cash Wallet Transaction
            ID: $transactionId
            Amount: $amount CASH
            Status: COMPLETED
            🌍🔑😋👑✍️💚🫆
        """.trimIndent()
        sendSms(context, phoneNumber, message)
    }
}

sealed class SmsCommand {
    data class Pay(val amount: Double, val recipient: String) : SmsCommand()
    object Balance : SmsCommand()
    object History : SmsCommand()
    data class Buy(val amount: Double) : SmsCommand()
}
