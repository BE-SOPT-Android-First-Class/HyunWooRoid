package com.l2hyunwoo.android.di

import com.l2hyunwoo.android.data.util.UrlStore
import com.l2hyunwoo.android.data.util.UrlStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideUrlStore(urlStore: UrlStoreImpl): UrlStore = urlStore

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
