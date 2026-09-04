package com.cash.wallet

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        try {
            setupViews()
            setupBiometric()
            updateWalletData()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun setupViews() {
        val btnAuth = findViewById<Button>(R.id.btnAuth)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnReceive = findViewById<Button>(R.id.btnReceive)
        val btnHistory = findViewById<Button>(R.id.btnHistory)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        
        btnAuth.setOnClickListener { authenticateUser() }
        btnSend.setOnClickListener { 
            Toast.makeText(this, "💸 Send Payment", Toast.LENGTH_SHORT).show() 
        }
        btnReceive.setOnClickListener { 
            Toast.makeText(this, "📥 Receive Address: CASH00BB0F6690D139792A7987BBDD9A4918FA", Toast.LENGTH_LONG).show() 
        }
        btnHistory.setOnClickListener { 
            Toast.makeText(this, "📊 Transaction History", Toast.LENGTH_SHORT).show() 
        }
        btnSettings.setOnClickListener { 
            Toast.makeText(this, "⚙️ Settings", Toast.LENGTH_SHORT).show() 
        }
    }
    
    private fun setupBiometric() {
        val executor = Executors.newSingleThreadExecutor()
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "✅ Authentication Successful!", Toast.LENGTH_SHORT).show()
                        updateWalletData()
                    }
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "❌ Authentication Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("CASH WALLET")
            .setSubtitle("Secure Authentication")
            .setDescription("Verify your identity")
            .setNegativeButtonText("Cancel")
            .build()
    }
    
    private fun authenticateUser() {
        try {
            biometricPrompt.authenticate(promptInfo)
        } catch (e: Exception) {
            Toast.makeText(this, "Biometric not available", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun updateWalletData() {
        try {
            val tvBalance = findViewById<TextView>(R.id.tvBalance)
            val tvWallets = findViewById<TextView>(R.id.tvWallets)
            val tvValue = findViewById<TextView>(R.id.tvValue)
            
            tvBalance.text = "💰 6,600,000,000,000 CASH"
            tvWallets.text = "📊 1,033 Wallets"
            tvValue.text = "💎 $132,000,000,000,000 USD"
        } catch (e: Exception) {
            // Ignore
        }
    }
}
