package com.cash.wallet.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cash.wallet.R
import com.cash.wallet.utils.SmsUtils
import kotlinx.coroutines.*

class SmsPaymentService : Service() {
    private val CHANNEL_ID = "sms_payment_channel"
    private val NOTIFICATION_ID = 1001
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        startSmsMonitoring()
    }

    private fun startSmsMonitoring() {
        serviceScope.launch {
            while (true) {
                // Monitor for SMS commands
                // In production: listen to SMS broadcasts
                delay(10000)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SMS Payments",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "SMS Payment Service"
                setShowBadge(false)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("💰 Cash Wallet")
            .setContentText("SMS Payments Active")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent): IBinder? = null
}
