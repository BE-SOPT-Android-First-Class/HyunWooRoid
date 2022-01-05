package com.l2hyunwoo.android.di

import com.l2hyunwoo.android.data.repository.LoginRepositoryImpl
import com.l2hyunwoo.android.data.repository.SignUpRepositoryImpl
import com.l2hyunwoo.android.data.repository.UserReposRepositoryImpl
import com.l2hyunwoo.android.domain.repository.LoginRepository
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import com.l2hyunwoo.android.domain.repository.UserReposRepository
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
    fun provideLoginRepository(
        loginRepository: LoginRepositoryImpl
    ): LoginRepository = loginRepository

    @Provides
    @Singleton
    fun provideSignUpRepository(
        signUpRepository: SignUpRepositoryImpl
    ): SignUpRepository = signUpRepository

    @Singleton
    @Provides
    fun provideUserReposRepository(
        userReposRepository: UserReposRepositoryImpl
    ): UserReposRepository = userReposRepository
}
