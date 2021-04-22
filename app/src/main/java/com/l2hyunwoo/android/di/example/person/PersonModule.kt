package com.l2hyunwoo.android.di.example.person

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
// @DisableInstallInCheck
class PersonModule {
    @Provides
    fun provideName(): String = "Charles"

    @Provides
    @Named("age")
    fun provideAge(): Int = 100
}