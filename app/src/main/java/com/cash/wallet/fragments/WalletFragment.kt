package com.cash.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import com.cash.wallet.R

class WalletFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        
        val tvBalance = view.findViewById<TextView>(R.id.tvBalance)
        val tvWallets = view.findViewById<TextView>(R.id.tvWallets)
        val tvValue = view.findViewById<TextView>(R.id.tvValue)
        
        tvBalance.text = "💰 6,600,000,000,000 CASH"
        tvWallets.text = "📊 33 Owner Wallets\n📊 1,000 Distribution Wallets"
        tvValue.text = "💎 $132,000,000,000,000 USD"
        
        view.findViewById<CardView>(R.id.cardCreateWallet).setOnClickListener {
            android.widget.Toast.makeText(requireContext(), "✅ New Wallet Created!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<CardView>(R.id.cardImportWallet).setOnClickListener {
            android.widget.Toast.makeText(requireContext(), "📥 Import Wallet", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        return view
    }
}
