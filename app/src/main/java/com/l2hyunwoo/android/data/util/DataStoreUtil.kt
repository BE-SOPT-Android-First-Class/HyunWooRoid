package com.l2hyunwoo.android.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

operator fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>): Flow<T?> {
    return this.data.map { it[key] }
}

val KEY_USER_ID = stringPreferencesKey("ID")
val KEY_USER_PASSWORD = stringPreferencesKey("PASSWORD")
