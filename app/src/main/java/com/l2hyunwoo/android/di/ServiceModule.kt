package com.l2hyunwoo.android.di

import com.l2hyunwoo.android.data.api.GithubApi
import com.l2hyunwoo.android.data.api.SoptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideSoptApi(@Named("SOPT_BASE_URL") retrofit: Retrofit): SoptApi =
        retrofit.create(SoptApi::class.java)

    @Singleton
    @Provides
    fun provideGithubApi(@Named("GITHUB_BASE_URL") retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)
}
