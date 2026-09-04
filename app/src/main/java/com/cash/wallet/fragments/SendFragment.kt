package com.cash.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cash.wallet.R

class SendFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_send, container, false)
        
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val btnSend = view.findViewById<Button>(R.id.btnSend)
        
        btnSend.setOnClickListener {
            val address = etAddress.text.toString()
            val amount = etAmount.text.toString()
            if (address.isNotEmpty() && amount.isNotEmpty()) {
                Toast.makeText(requireContext(), "💸 Sent $amount CASH to $address", Toast.LENGTH_LONG).show()
                etAddress.text.clear()
                etAmount.text.clear()
            } else {
                Toast.makeText(requireContext(), "❌ Please enter address and amount", Toast.LENGTH_SHORT).show()
            }
        }
        
        return view
    }
}
