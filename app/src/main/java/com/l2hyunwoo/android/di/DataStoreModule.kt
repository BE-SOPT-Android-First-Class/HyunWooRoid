package com.l2hyunwoo.android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.l2hyunwoo.android.data.util.EncryptedDataStore
import com.l2hyunwoo.android.presentation.util.CipherToolBox
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("GithubDataStore") }

    @Singleton
    @Provides
    fun provideCipherToolBox(@ApplicationContext context: Context) = CipherToolBox(context)

    @Singleton
    @Provides
    fun provideEncryptedDataStore(
        cipherToolBox: CipherToolBox,
        dataStore: DataStore<Preferences>
    ) = EncryptedDataStore(dataStore, cipherToolBox)
}
