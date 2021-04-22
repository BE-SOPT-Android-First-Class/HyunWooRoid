package com.l2hyunwoo.android.di.dagger.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.l2hyunwoo.android.data.repository.LoginRepositoryImpl
import com.l2hyunwoo.android.data.repository.SignUpRepositoryImpl
import com.l2hyunwoo.android.domain.repository.LoginRepository
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
class ApplicationModule() {

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("GithubDataStore") }
}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun bindSignUpRepository(repository: SignUpRepositoryImpl): SignUpRepository
}