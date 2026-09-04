package com.cash.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val btnAuth = findViewById<Button>(R.id.btnAuth)
        val btnSend = findViewById<Button>(R.id.btnSend)
        
        tvBalance.text = "💰 Balance: 6,600,000,000,000 CASH"
        
        setupBiometric()
        
        btnAuth.setOnClickListener {
            authenticateUser()
        }
        
        btnSend.setOnClickListener {
            Toast.makeText(this, "✅ Payment sent successfully!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupBiometric() {
        val executor = Executors.newSingleThreadExecutor()
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "✅ Authentication Successful!", Toast.LENGTH_SHORT).show()
            }
            
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@MainActivity, "❌ Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        })
        
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("CASH WALLET")
            .setSubtitle("Verify your identity")
            .setDescription("Use fingerprint or face recognition")
            .setNegativeButtonText("Cancel")
            .build()
    }
    
    private fun authenticateUser() {
        biometricPrompt.authenticate(promptInfo)
    }
}
