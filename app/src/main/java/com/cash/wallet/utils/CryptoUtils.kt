package com.cash.wallet.utils

import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val KEY_SIZE = 256
    private const val TAG_LENGTH = 128
    private const val IV_LENGTH = 12

    private var secretKey: SecretKey? = null

    fun generateKey(): String {
        val keyGen = KeyGenerator.getInstance(ALGORITHM)
        keyGen.init(KEY_SIZE)
        secretKey = keyGen.generateKey()
        return Base64.getEncoder().encodeToString(secretKey?.encoded ?: ByteArray(0))
    }

    fun encryptData(data: String): String {
        val key = secretKey ?: run {
            generateKey()
            secretKey
        }
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(IV_LENGTH)
        java.security.SecureRandom().nextBytes(iv)
        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, spec)
        val encrypted = cipher.doFinal(data.toByteArray())
        val combined = iv + encrypted
        return Base64.getEncoder().encodeToString(combined)
    }

    fun decryptData(encryptedData: String): String {
        val key = secretKey ?: return ""
        val combined = Base64.getDecoder().decode(encryptedData)
        val iv = combined.sliceArray(0 until IV_LENGTH)
        val encrypted = combined.sliceArray(IV_LENGTH until combined.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted)
    }

    fun sha3_512(input: String): String {
        val digest = MessageDigest.getInstance("SHA3-512")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun generateWalletAddress(): String {
        val entropy = java.security.SecureRandom().nextBytes(32)
        val timestamp = System.currentTimeMillis()
        return "CW-${sha3_512("$timestamp:$entropy").substring(0, 16)}"
    }
}
