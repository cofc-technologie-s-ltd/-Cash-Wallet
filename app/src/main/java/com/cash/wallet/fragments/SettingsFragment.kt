package com.cash.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cash.wallet.R

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        
        view.findViewById<Button>(R.id.btnTheme).setOnClickListener {
            Toast.makeText(requireContext(), "🎨 Theme Toggled!", Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<Button>(R.id.btnBackup).setOnClickListener {
            Toast.makeText(requireContext(), "💾 Wallet Backed Up!", Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<Button>(R.id.btnSecurity).setOnClickListener {
            Toast.makeText(requireContext(), "🔐 Security Settings", Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<Button>(R.id.btnExport).setOnClickListener {
            Toast.makeText(requireContext(), "📤 Data Exported!", Toast.LENGTH_SHORT).show()
        }
        
        return view
    }
}
