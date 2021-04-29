package com.l2hyunwoo.android.di.example.helloworld

import dagger.Module
import dagger.Provides

@Module
// @DisableInstallInCheck
class HelloWorldModule {
    @Provides
    fun provideHelloWorld(): String {
        return "Hello World!"
    }
}
