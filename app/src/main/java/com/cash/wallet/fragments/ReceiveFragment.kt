package com.cash.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cash.wallet.R

class ReceiveFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_receive, container, false)
        
        val tvAddress = view.findViewById<TextView>(R.id.tvAddress)
        val btnGenerate = view.findViewById<Button>(R.id.btnGenerate)
        
        tvAddress.text = "CASH00BB0F6690D139792A7987BBDD9A4918FA"
        
        btnGenerate.setOnClickListener {
            val random = (Math.random() * 1000000).toInt()
            tvAddress.text = "CASH${String.format("%040d", random)}"
            android.widget.Toast.makeText(requireContext(), "✅ New Address Generated!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        view.findViewById<Button>(R.id.btnCopy).setOnClickListener {
            android.widget.Toast.makeText(requireContext(), "📋 Address Copied!", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        return view
    }
}
