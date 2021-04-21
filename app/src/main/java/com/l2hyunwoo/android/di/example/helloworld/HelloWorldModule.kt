package com.l2hyunwoo.android.di.example.helloworld

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
class HelloWorldModule {
    @Provides
    fun provideHelloWorld(): String {
        return "Hello World!"
    }
}
