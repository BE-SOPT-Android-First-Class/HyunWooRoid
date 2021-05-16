package com.l2hyunwoo.android.di.dagger.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.l2hyunwoo.android.data.api.SoptApi
import com.l2hyunwoo.android.data.datasource.GithubDataSource
import com.l2hyunwoo.android.data.datasource.SoptDataSource
import com.l2hyunwoo.android.data.local.MockGithubDataSource
import com.l2hyunwoo.android.data.remote.SoptDataSourceImpl
import com.l2hyunwoo.android.data.repository.LoginRepositoryImpl
import com.l2hyunwoo.android.data.repository.SignUpRepositoryImpl
import com.l2hyunwoo.android.data.repository.UserReposRepositoryImpl
import com.l2hyunwoo.android.data.util.EncryptedDataStore
import com.l2hyunwoo.android.data.util.UrlStore
import com.l2hyunwoo.android.data.util.UrlStoreImpl
import com.l2hyunwoo.android.domain.repository.LoginRepository
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import com.l2hyunwoo.android.domain.repository.UserReposRepository
import com.l2hyunwoo.android.presentation.util.CipherToolBox
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        ApplicationModuleBinds::class,
        DataStoreModule::class,
        DataSourceModule::class,
        UrlStoreModule::class,
        ServiceModule::class
    ]
)
class ApplicationModule() {
    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

    @Singleton
    @Provides
    @Named("SOPT_BASE_URL")
    fun provideSoptRetrofit(urlStore: UrlStore): Retrofit {
        return Retrofit.Builder().baseUrl(urlStore.getSoptBaseUrl()).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    @Named("GITHUB_BASE_URL")
    fun provideGithubRetrofit(urlStore: UrlStore): Retrofit {
        return Retrofit.Builder().baseUrl(urlStore.getGithubBaseUrl()).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun bindSignUpRepository(repository: SignUpRepositoryImpl): SignUpRepository

    @Singleton
    @Binds
    abstract fun bindGithubDataSource(dataSource: MockGithubDataSource): GithubDataSource

    @Singleton
    @Binds
    abstract fun bindUserReposRepository(userReposRepository: UserReposRepositoryImpl): UserReposRepository
}

@Module
class DataStoreModule() {
    @Singleton
    @Provides
    fun provideCipherToolBox(context: Context) = CipherToolBox(context)

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("GithubDataStore") }

    @Singleton
    @Provides
    fun provideEncryptedDataStore(
        cipherToolBox: CipherToolBox,
        dataStore: DataStore<Preferences>
    ) = EncryptedDataStore(dataStore, cipherToolBox)
}

@Module
abstract class UrlStoreModule {
    @Singleton
    @Binds
    abstract fun provideUrlStore(urlStore: UrlStoreImpl): UrlStore
}

@Module
class ServiceModule {
    @Singleton
    @Provides
    fun provideSoptApi(@Named("SOPT_BASE_URL") retrofit: Retrofit): SoptApi =
        retrofit.create(SoptApi::class.java)
}

@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindSoptDataSource(soptDataSourceImpl: SoptDataSourceImpl): SoptDataSource
}
