package com.cash.wallet.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.widget.Toast
import com.cash.wallet.utils.SmsUtils

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Telephony.Sms.Intents.getMessagesFromIntent(intent)
            } else {
                // Fallback for older versions
                arrayOf()
            }

            for (smsMessage in messages) {
                val messageBody = smsMessage.messageBody ?: continue
                val sender = smsMessage.originatingAddress ?: "Unknown"

                val command = SmsUtils.parseSmsCommand(messageBody)
                if (command != null) {
                    Toast.makeText(context, "📨 SMS Command Received: $messageBody", Toast.LENGTH_LONG).show()
                    // Process command
                    processCommand(context, command, sender)
                }
            }
        }
    }

    private fun processCommand(context: Context, command: SmsUtils.SmsCommand, sender: String) {
        when (command) {
            is SmsUtils.SmsCommand.Pay -> {
                // Process payment
                Toast.makeText(context, "💳 Payment: ${command.amount} CASH to ${command.recipient}", Toast.LENGTH_LONG).show()
            }
            is SmsUtils.SmsCommand.Balance -> {
                Toast.makeText(context, "💰 Balance: 100.00 CASH", Toast.LENGTH_LONG).show()
            }
            is SmsUtils.SmsCommand.History -> {
                Toast.makeText(context, "📊 Last 5 transactions...", Toast.LENGTH_LONG).show()
            }
            is SmsUtils.SmsCommand.Buy -> {
                Toast.makeText(context, "🪙 Buying ${command.amount} CASH...", Toast.LENGTH_LONG).show()
            }
        }
    }
}
