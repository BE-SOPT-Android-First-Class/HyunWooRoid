package com.l2hyunwoo.android.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.l2hyunwoo.android.presentation.util.CipherToolBox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EncryptedDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cipherToolBox: CipherToolBox
) {
    private val securityKeyAlias = "GithubDataStore"
    private val bytesToStringSeparator = "|"

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun set(key: Preferences.Key<String>, value: String) {
        dataStore.secureEdit(value) { pref, encryptedValue -> pref[key] = encryptedValue }
    }

    fun get(key: Preferences.Key<String>) = dataStore.data.secureMap<String> { it[key].orEmpty() }

    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map {
            val decryptedValue = cipherToolBox.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            Json { encodeDefaults = true }.decodeFromString(decryptedValue)
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue =
                cipherToolBox.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }
}
