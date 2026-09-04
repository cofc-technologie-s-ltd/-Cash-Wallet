package com.cash.wallet

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setupViews()
        setupBiometric()
        setupWalletAnimation()
        updateWalletData()
        
        // שינוי צבעים
        findViewById<Button>(R.id.btnThemeToggle).setOnClickListener {
            toggleTheme()
        }
    }
    
    private fun setupViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        
        val fragments = listOf(
            WalletFragment(),
            SendFragment(),
            ReceiveFragment(),
            HistoryFragment(),
            AnalyticsFragment(),
            SettingsFragment()
        )
        
        val adapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = adapter
        
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "💼 Wallet"
                1 -> "💸 Send"
                2 -> "📥 Receive"
                3 -> "📊 History"
                4 -> "📈 Analytics"
                5 -> "⚙️ Settings"
                else -> ""
            }
        }.attach()
    }
    
    private fun setupBiometric() {
        val executor = Executors.newSingleThreadExecutor()
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity, "✅ Authenticated!", Toast.LENGTH_SHORT).show()
                    updateWalletData()
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
        
        // אימות אוטומטי
        biometricPrompt.authenticate(promptInfo)
    }
    
    private fun toggleTheme() {
        // החלפה בין מצב יום ללילה
        val currentTheme = if (resources.configuration.uiMode and 0x30 == 0x20) "dark" else "light"
        Toast.makeText(this, "🎨 Theme: ${if (currentTheme == "dark") "Light" else "Dark"}", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupWalletAnimation() {
        val walletCard = findViewById<CardView>(R.id.walletCard)
        walletCard.setOnClickListener {
            Toast.makeText(this, "💰 Wallet opened!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun updateWalletData() {
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val tvWallets = findViewById<TextView>(R.id.tvWallets)
        val tvValue = findViewById<TextView>(R.id.tvValue)
        val tvTotalSupply = findViewById<TextView>(R.id.tvTotalSupply)
        
        tvBalance.text = "💰 6,600,000,000,000 CASH"
        tvWallets.text = "📊 1,033 Wallets"
        tvValue.text = "💎 $132,000,000,000,000 USD"
        tvTotalSupply.text = "🔄 Total Supply: 6.6 Trillion CASH"
    }
}
