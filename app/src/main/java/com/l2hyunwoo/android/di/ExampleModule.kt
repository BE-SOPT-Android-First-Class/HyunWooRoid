package com.l2hyunwoo.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
class ExampleModule {
    @Provides
    fun provideHelloWorld(): String {
        return "Hello World!"
    }
}
