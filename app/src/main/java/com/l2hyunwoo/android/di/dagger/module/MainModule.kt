package com.l2hyunwoo.android.di.dagger.module

import com.l2hyunwoo.android.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun homeActivity(): MainActivity
}