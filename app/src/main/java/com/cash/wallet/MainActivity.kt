package com.cash.wallet
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import java.util.concurrent.Executors
import java.text.NumberFormat
import java.util.Locale
class MainActivity : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val walletData = WalletData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val tvWallets = findViewById<TextView>(R.id.tvWallets)
        val tvValue = findViewById<TextView>(R.id.tvValue)
        val tvAddress = findViewById<TextView>(R.id.tvAddress)
        val btnAuth = findViewById<Button>(R.id.btnAuth)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnReceive = findViewById<Button>(R.id.btnReceive)
        val btnHistory = findViewById<Button>(R.id.btnHistory)
        val btnStake = findViewById<Button>(R.id.btnStake)
        tvBalance.text = "💰 ${walletData.getFormattedBalance()}"
        tvWallets.text = "📊 ${walletData.totalWallets} Wallets"
        tvValue.text = "💎 ${walletData.getFormattedValue()}"
        tvAddress.text = "📍 ${walletData.getFirstAddress()}"
        setupBiometric()
        btnAuth.setOnClickListener { authenticateUser() }
        btnSend.setOnClickListener { Toast.makeText(this, "💸 Send Payment", Toast.LENGTH_SHORT).show() }
        btnReceive.setOnClickListener { Toast.makeText(this, "📥 Receive - Share your address", Toast.LENGTH_SHORT).show() }
        btnHistory.setOnClickListener { Toast.makeText(this, "📊 ${walletData.totalTransactions} transactions", Toast.LENGTH_SHORT).show() }
        btnStake.setOnClickListener { Toast.makeText(this, "🔒 Stake CASH - ${walletData.stakingAPY}% APY", Toast.LENGTH_SHORT).show() }
    }
    private fun setupBiometric() {
        val executor = Executors.newSingleThreadExecutor()
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "✅ Authenticated!", Toast.LENGTH_SHORT).show()
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@MainActivity, "❌ Failed", Toast.LENGTH_SHORT).show()
            }
        })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("CASH WALLET")
            .setSubtitle("Secure Authentication")
            .setDescription("Verify your identity")
            .setNegativeButtonText("Cancel")
            .build()
    }
    private fun authenticateUser() { biometricPrompt.authenticate(promptInfo) }
    inner class WalletData {
        val totalWallets = 1033
        val totalBalance = 6_600_000_000_000L
        val totalValue = 132_000_000_000_000L
        val totalTransactions = 10_000_000
        val stakingAPY = 12.5
        fun getFormattedBalance(): String = NumberFormat.getNumberInstance(Locale.US).format(totalBalance) + " CASH"
        fun getFormattedValue(): String = "$${NumberFormat.getNumberInstance(Locale.US).format(totalValue)} USD"
        fun getFirstAddress(): String = "CASH00BB0F6690D139792A7987BBDD9A4918FA"
    }
}
