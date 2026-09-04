package com.cash.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cash.wallet.R

class HistoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        val transactions = listOf(
            "💰 Received 1,000,000 CASH from Wallet #33",
            "💸 Sent 500,000 CASH to Wallet #12",
            "💰 Received 2,500,000 CASH from Wallet #7",
            "💸 Sent 750,000 CASH to Wallet #25",
            "💰 Received 10,000,000 CASH from Distribution"
        )
        
        val adapter = TransactionAdapter(transactions)
        recyclerView.adapter = adapter
        
        return view
    }
    
    class TransactionAdapter(private val transactions: List<String>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvTransaction: TextView = itemView.findViewById(android.R.id.text1)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTransaction.text = transactions[position]
        }
        override fun getItemCount(): Int = transactions.size
    }
}
