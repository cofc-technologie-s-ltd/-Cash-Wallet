package com.cash.wallet.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.cash.wallet.R
import com.cash.wallet.databinding.ActivityMainBinding
import com.cash.wallet.services.QuantumSecurityService
import com.cash.wallet.utils.BiometricUtils
import com.cash.wallet.utils.LicenseUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if biometric is available and authenticate
        if (BiometricUtils.isBiometricAvailable(this)) {
            authenticateUser()
        } else {
            // Fallback to PIN/Password
            showPinDialog()
        }
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        startQuantumSecurity()
        setupUI()
        setupListeners()
    }

    private fun authenticateUser() {
        BiometricUtils.authenticate(
            activity = this,
            title = "🔐 Secure Access",
            subtitle = "Authenticate to access Cash Wallet",
            onSuccess = {
                Toast.makeText(this, "✅ Authentication Successful", Toast.LENGTH_SHORT).show()
            },
            onError = { error ->
                Toast.makeText(this, "❌ $error", Toast.LENGTH_LONG).show()
                finish()
            }
        )
    }

    private fun showPinDialog() {
        // Simple PIN fallback
        // In production: use secure PIN input
    }

    private fun startQuantumSecurity() {
        val intent = Intent(this, QuantumSecurityService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun setupUI() {
        binding.walletAddress.text = "CW-${System.currentTimeMillis()}"
        binding.balance.text = "0.00 CASH"
    }

    private fun setupListeners() {
        binding.sendButton.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        binding.receiveButton.setOnClickListener {
            Toast.makeText(this, "📥 Receive: Show QR Code", Toast.LENGTH_SHORT).show()
        }
        binding.historyButton.setOnClickListener {
            Toast.makeText(this, "📊 Transaction History", Toast.LENGTH_SHORT).show()
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
