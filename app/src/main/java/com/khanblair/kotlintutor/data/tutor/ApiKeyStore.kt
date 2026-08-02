package com.khanblair.kotlintutor.data.tutor

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface ApiKeyStore {
    fun getApiKey(): String?
    fun setApiKey(key: String)
    fun clearApiKey()
}

/** Stores the user's own DeepSeek API key on-device via the Android Keystore. Never hardcoded, never committed. */
class EncryptedApiKeyStore(context: Context) : ApiKeyStore {
    private val prefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    override fun getApiKey(): String? = prefs.getString(KEY_API_KEY, null)?.takeIf { it.isNotBlank() }

    override fun setApiKey(key: String) {
        prefs.edit().putString(KEY_API_KEY, key).apply()
    }

    override fun clearApiKey() {
        prefs.edit().remove(KEY_API_KEY).apply()
    }

    private companion object {
        const val PREFS_NAME = "kotlintutor_secure_prefs"
        const val KEY_API_KEY = "deepseek_api_key"
    }
}
