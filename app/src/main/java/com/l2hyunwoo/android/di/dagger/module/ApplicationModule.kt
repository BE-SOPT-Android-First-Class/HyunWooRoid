package com.l2hyunwoo.android.di.dagger.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.l2hyunwoo.android.data.datasource.GithubDataSource
import com.l2hyunwoo.android.data.datasource.MockGithubDataSource
import com.l2hyunwoo.android.data.repository.LoginRepositoryImpl
import com.l2hyunwoo.android.data.repository.SignUpRepositoryImpl
import com.l2hyunwoo.android.data.repository.UserReposRepositoryImpl
import com.l2hyunwoo.android.domain.repository.LoginRepository
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import com.l2hyunwoo.android.domain.repository.UserReposRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

@Module(includes = [ApplicationModuleBinds::class])
class ApplicationModule() {
    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideApplicationContext(context: Context): Context = context.applicationContext

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

    @Singleton
    @Binds
    abstract fun bindGithubDataSource(dataSource: MockGithubDataSource): GithubDataSource

    @Singleton
    @Binds
    abstract fun bindUserReposRepository(userReposRepository: UserReposRepositoryImpl): UserReposRepository
}