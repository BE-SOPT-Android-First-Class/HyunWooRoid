package com.l2hyunwoo.android.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.l2hyunwoo.android.data.repository.LoginRepositoryImpl
import com.l2hyunwoo.android.data.repository.SignUpRepositoryImpl
import com.l2hyunwoo.android.domain.repository.LoginRepository
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(dataStore: DataStore<Preferences>): LoginRepository =
        LoginRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideSignUpRepository(dataStore: DataStore<Preferences>): SignUpRepository =
        SignUpRepositoryImpl(dataStore)
}
