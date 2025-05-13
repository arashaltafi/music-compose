package ir.arash.altafi.musiccompose.utils

import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import kotlin.random.Random

class EncryptionUtils() {

    private val ivSize = 12 // 12 bytes IV for AES-GCM
    private val tagSize = 128 // 128-bit authentication tag for AES-GCM

    // Encrypt the given value using AES/GCM/NoPadding
    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = getSecretKey()

        // Generate random IV
        val iv = ByteArray(ivSize).also { Random.nextBytes(it) }
        val spec = GCMParameterSpec(tagSize, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

        // Encrypt
        val encrypted = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))

        // Prefix IV to ciphertext and Base64-encode
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    // Decrypt or fallback to raw if input wasn’t actually encrypted
    fun decrypt(encryptedValue: String): String {
        return try {
            // Base64 decode; if this fails, we’ll go to catch{} below
            val decoded = Base64.decode(encryptedValue, Base64.DEFAULT)

            // If too short to even hold the IV, assume it’s raw and return it
            if (decoded.size <= ivSize) return encryptedValue

            val iv = decoded.copyOfRange(0, ivSize)
            val cipherBytes = decoded.copyOfRange(ivSize, decoded.size)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val secretKey = getSecretKey()
            val spec = GCMParameterSpec(tagSize, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            String(cipher.doFinal(cipherBytes), StandardCharsets.UTF_8)
        } catch (t: Throwable) {
            // any parsing / crypto error → just return the original string
            encryptedValue
        }
    }

    private fun getSecretKey(): SecretKeySpec {
        // your 16-byte AES key
        val keyString = "my_secret_key_1234"
        val raw = keyString.toByteArray(StandardCharsets.UTF_8)
        val key = raw.copyOf(16)  // pad/trim to 16 bytes
        return SecretKeySpec(key, "AES")
    }
}
