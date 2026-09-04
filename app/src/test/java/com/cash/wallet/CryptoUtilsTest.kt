package com.cash.wallet

import com.cash.wallet.utils.CryptoUtils
import org.junit.Assert.*
import org.junit.Test

class CryptoUtilsTest {
    @Test
    fun testEncryptionDecryption() {
        val original = "Cash Wallet Test Data"
        val encrypted = CryptoUtils.encryptData(original)
        val decrypted = CryptoUtils.decryptData(encrypted)
        assertEquals(original, decrypted)
    }

    @Test
    fun testSHA3_512() {
        val input = "test"
        val hash = CryptoUtils.sha3_512(input)
        assertTrue(hash.length == 128) // 512 bits = 128 hex chars
    }

    @Test
    fun testWalletAddressGeneration() {
        val address = CryptoUtils.generateWalletAddress()
        assertTrue(address.startsWith("CW-"))
        assertTrue(address.length >= 19)
    }
}
