package com.cash.wallet.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE from = :address OR to = :address ORDER BY timestamp DESC")
    fun getTransactionsForAddress(address: String): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentTransactions(limit: Int): List<Transaction>

    @Query("SELECT * FROM transactions WHERE hash = :hash")
    suspend fun getTransactionByHash(hash: String): Transaction?

    @Query("DELETE FROM transactions WHERE timestamp < :timestamp")
    suspend fun deleteOldTransactions(timestamp: Long)

    @Query("SELECT SUM(amount) FROM transactions WHERE to = :address AND status = 'COMPLETED'")
    suspend fun getTotalReceived(address: String): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE from = :address AND status = 'COMPLETED'")
    suspend fun getTotalSent(address: String): Double?
}
