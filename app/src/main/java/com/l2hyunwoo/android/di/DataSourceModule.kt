package com.l2hyunwoo.android.di

import com.l2hyunwoo.android.data.datasource.GithubDataSource
import com.l2hyunwoo.android.data.datasource.SoptDataSource
import com.l2hyunwoo.android.data.local.MockGithubDataSource
import com.l2hyunwoo.android.data.remote.SoptDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideSoptDataSource(soptDataSource: SoptDataSourceImpl): SoptDataSource = soptDataSource

    @Singleton
    @Provides
    fun bindGithubDataSource(dataSource: MockGithubDataSource): GithubDataSource = dataSource
}
